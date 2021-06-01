package com.kevinkons.location.getlocationsstrategy;

import com.kevinkons.location.entity.Location;

public interface GenerateLocationsFileStrategy {
    String generateFile(Location[] locations, String fileName);
}
