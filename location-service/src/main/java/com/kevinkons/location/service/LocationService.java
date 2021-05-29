package com.kevinkons.location.service;

import com.kevinkons.location.controller.LocationController;
import com.kevinkons.location.entity.Location;
import com.kevinkons.location.exception.FormatNotSupportedException;
import com.kevinkons.location.exception.LocationNotFoundException;
import com.kevinkons.location.getlocationsstrategy.TransformLocationsStrategy;
import com.kevinkons.location.getlocationsstrategy.TransformLocationsStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class LocationService {

    Logger logger = Logger.getLogger(LocationService.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransformLocationsStrategyFactory locationsStrategyFactory;

    public static final String citiesPath = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";
    public static final String citiesByStatePath = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios";

    public Location[] getAllLocations() {
        logger.info("Inside getAllLocations method of LocationService");
        return restTemplate.getForObject(citiesPath,
                Location[].class);
    }

    public String getAllLocations(String format) {
        logger.info("Inside getAllLocations method of LocationService with format as " + format);
        TransformLocationsStrategy strategy = locationsStrategyFactory.getStrategy(format).orElseThrow(() ->
            new FormatNotSupportedException(String.format("%s format is not supported", format)));

        return strategy.transformLocations(getAllLocations(),
                Instant.now().toString().replace(".", "").replace(":", ""));
    }

    @Cacheable("location")
    public String findLocation(String state, String city) {
        logger.info(String.format(
                "Inside findLocation method of LocationService with state as %s and city as %s", state, city));
        Location[] locations = restTemplate.getForObject(
                String.format(citiesByStatePath, state),
                Location[].class);

        if(locations == null)
            throw new LocationNotFoundException("State not found");

        Location location =  Arrays.stream(locations).filter( loc ->
                loc.getNomeCidade().equalsIgnoreCase(city)).findFirst()
                .orElseThrow(() -> new LocationNotFoundException("City not found"));

        return location.getIdCidade();
    }
}
