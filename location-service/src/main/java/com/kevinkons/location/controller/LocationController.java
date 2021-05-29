package com.kevinkons.location.controller;

import com.kevinkons.location.entity.Location;
import com.kevinkons.location.service.FileStorageService;
import com.kevinkons.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/locations")
public class LocationController {

    Logger logger = Logger.getLogger(LocationController.class.getName());

    @Autowired
    private LocationService locationService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/")
    public Location[] getLocations() {
        logger.info("Inside of getLocations method of LocationController");
        return locationService.getAllLocations();
    }

    @GetMapping("/{format}")
    public ResponseEntity<Resource> getLocations(HttpServletRequest request,
                                                 @PathVariable String format) {
        logger.info(String.format(
                "Inside of getLocations method of LocationController with format as %s.", format));

        String fileName = locationService.getAllLocations(format);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.log(Level.INFO, "Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "municipios." + format + "\"")
                .body(resource);
    }

    @GetMapping("/{state}/{city}")
    public ResponseEntity<?> findLocation(@PathVariable String state,
                                          @PathVariable String city) {
        logger.info(String.format(
                "Inside of findLocation method of LocationController with state as %s, and city as %s.", state, city));
        return ResponseEntity.ok()
                .body(locationService.findLocation(state, city));
    }
}
