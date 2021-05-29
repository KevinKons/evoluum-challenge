package com.kevinkons.location.service;

import com.kevinkons.location.entity.Location;
import com.kevinkons.location.getlocationsstrategy.TransformLocationsStrategy;

public class MockTransformLocationStrategy implements TransformLocationsStrategy {
    @Override
    public String transformLocations(Location[] locations, String fileName) {
        return "mocked strategy";
    }
}
