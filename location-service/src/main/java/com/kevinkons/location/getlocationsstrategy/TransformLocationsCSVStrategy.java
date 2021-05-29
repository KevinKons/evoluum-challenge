package com.kevinkons.location.getlocationsstrategy;

import com.kevinkons.location.entity.Location;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TransformLocationsCSVStrategy implements TransformLocationsStrategy {

    Logger logger = Logger.getLogger(TransformLocationsCSVStrategy.class.getName());

    private final String labels = "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado";

    @Override
    public String transformLocations(Location[] locations, String fileName) {
        logger.info("inside of transformLocations method of TransformLocationsCSVStrategy with fileName as " + fileName);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("files/" + fileName);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            bufferedOutputStream.write(labels.concat("\n").getBytes());

            List<byte[]> locationsBytes = Arrays.stream(locations).
                    map(location -> location.toString().concat("\n").getBytes()).collect(Collectors.toList());
            for(byte[] location : locationsBytes)
                bufferedOutputStream.write(location);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }
}
