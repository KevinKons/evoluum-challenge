package com.kevinkons.location.getlocationsstrategy;

import com.kevinkons.location.entity.Location;

public interface TransformLocationsStrategy {
    String transformLocations(Location[] locations, String fileName);
}
