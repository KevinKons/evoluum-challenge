package com.kevinkons.location.getlocationsstrategy;

import com.kevinkons.location.entity.Location;
import com.kevinkons.location.exception.FileStorageException;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class GenerateLocationsCSVFileStrategy implements GenerateLocationsFileStrategy {

    Logger logger = Logger.getLogger(GenerateLocationsCSVFileStrategy.class.getName());

    private final String labels = "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado\n";

    @Override
    public String generateFile(Location[] locations, String fileName) {
        logger.info("inside of transformLocations method of TransformLocationsCSVStrategy with fileName as " + fileName);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("files/" + fileName);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte[] content = generateCsvContent(locations).getBytes();
            bufferedOutputStream.write(content);
        } catch (IOException e) {
            throw new FileStorageException("Error while generating file");
        } finally{
            try {
                if(bufferedOutputStream != null)
                    bufferedOutputStream.close();
                if(fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                throw new FileStorageException("Error while generating file");
            }
        }
        return fileName;
    }

    private String generateCsvContent(Location[] locations) {
        StringBuffer csvContent = new StringBuffer(labels);
        Arrays.stream(locations).forEach(location -> csvContent.append(location.toString()));
        return csvContent.toString();
    }
}
