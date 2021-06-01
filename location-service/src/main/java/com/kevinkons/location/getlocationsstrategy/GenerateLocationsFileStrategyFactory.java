package com.kevinkons.location.getlocationsstrategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class GenerateLocationsFileStrategyFactory {

    Logger logger = Logger.getLogger(GenerateLocationsFileStrategyFactory.class.getName());

    private final Map<String, GenerateLocationsFileStrategy> strategies = new HashMap<>();

    public GenerateLocationsFileStrategyFactory() {
        strategies.put("csv", new GenerateLocationsCSVFileStrategy());
        strategies.put("xml", new GenerateLocationsXMLFileStrategy());
    }

    public Optional<GenerateLocationsFileStrategy> getStrategy(String format) {
        logger.info("Inside getStrategy method of TransformLocationsStrategyFactory with format as " + format);
        return Optional.ofNullable(strategies.get(format));
    }
}
