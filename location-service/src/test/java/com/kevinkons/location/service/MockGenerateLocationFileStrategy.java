package com.kevinkons.location.service;

import com.kevinkons.location.entity.Location;
import com.kevinkons.location.getlocationsstrategy.GenerateLocationsFileStrategy;

public class MockGenerateLocationFileStrategy implements GenerateLocationsFileStrategy {
    @Override
    public String generateFile(Location[] locations, String fileName) {
        return "mocked strategy";
    }
}
