package com.kevinkons.cloud.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/locationServiceFallBack")
    public String locationsFallBackMethod() {
        return "Locations service is taking longer than expected. Please try again later";
    }
}
