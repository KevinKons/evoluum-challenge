package com.kevinkons.location.getlocationsstrategy;

import com.kevinkons.location.service.FileStorageService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class TransformLocationsStrategyFactory {

    Logger logger = Logger.getLogger(TransformLocationsStrategyFactory.class.getName());

    private final Map<String, TransformLocationsStrategy> strategies = new HashMap<>();

    public TransformLocationsStrategyFactory() {
        strategies.put("csv", new TransformLocationsCSVStrategy());
        strategies.put("xml", new TransformLocationsXMLStrategy());
    }

    public Optional<TransformLocationsStrategy> getStrategy(String format) {
        logger.info("Inside getStrategy method of TransformLocationsStrategyFactory with format as " + format);
        return Optional.ofNullable(strategies.get(format));
    }
}
