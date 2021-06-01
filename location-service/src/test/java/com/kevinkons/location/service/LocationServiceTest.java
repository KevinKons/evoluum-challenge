package com.kevinkons.location.service;

import com.kevinkons.location.entity.Location;
import com.kevinkons.location.exception.FormatNotSupportedException;
import com.kevinkons.location.exception.LocationNotFoundException;

import com.kevinkons.location.getlocationsstrategy.GenerateLocationsFileStrategyFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

    @InjectMocks
    LocationService locationService = new LocationService();

    @Mock
    RestTemplate restTemplate;

    @Mock
    GenerateLocationsFileStrategyFactory generateLocationsFileStrategyFactory;

    private static Location[] locations;
    private static Location location1;
    private static Location location2;
    private static Location location3;

    @BeforeClass
    public static void before() {
        location1 = new Location("42", "SC", "Sul", "Biguaçu", "Grande Florianópolis", "Biguaçu/SC", "234324");
        location2 = new Location("42", "SC", "Sul", "Biguaçu", "Grande Florianópolis", "Biguaçu/SC", "234324");
        location3 = new Location("42", "SC", "Sul", "Biguaçu", "Grande Florianópolis", "Biguaçu/SC", "234324");
        locations = new Location[]{ location1, location2, location3 };
    }

    @Test
    public void findLocationWhenLocationExists() {
        when(restTemplate.getForObject(String.format(LocationService.citiesByStatePath, "sc"), Location[].class))
                .thenReturn(locations);

        String result = locationService.findLocation("sc", "biguaçu");

        assertEquals(location1.getIdCidade(), result);
    }

    @Test
    public void findLocationWhenStateDoesntExists() {
        when(restTemplate.getForObject( String.format(LocationService.citiesByStatePath, "sf"), Location[].class))
                .thenReturn(null);

        Exception exception = assertThrows(LocationNotFoundException.class, () ->
            locationService.findLocation("sf", "biguaçu")
        );

        String expectedMessage = "State not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findLocationWhenCityDoesntExists() {
        when(restTemplate.getForObject(String.format(LocationService.citiesByStatePath, "sc"), Location[].class))
                .thenReturn(locations);

        Exception exception = assertThrows(LocationNotFoundException.class, () ->
            locationService.findLocation("sc", "rio de janeiro")
        );

        String expectedMessage = "City not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAllLocations() {
        when(restTemplate.getForObject(LocationService.citiesPath, Location[].class)).thenReturn(locations);

        Location[] result = locationService.generateAllLocationsFile();

        assertEquals(result[0].getNomeCidade(), location1.getNomeCidade());
        assertEquals(result[1].getNomeCidade(), location2.getNomeCidade());
        assertEquals(result[2].getNomeCidade(), location3.getNomeCidade());
        assertEquals(result.length, locations.length);
    }

    @Test
    public void getAllLocationsWhenFormatIsNotSupported() {
        when(generateLocationsFileStrategyFactory.getStrategy("pdf")).thenReturn(Optional.empty());

        Exception exception = assertThrows(FormatNotSupportedException.class, () ->
            locationService.generateAllLocationsFile("pdf")
        );

        String expectedMessage = "pdf format is not supported";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getAllLocationsWhenFormatIsSupported() {
        when(generateLocationsFileStrategyFactory.getStrategy("csv"))
                .thenReturn(Optional.of(new MockGenerateLocationFileStrategy()));

        String result = locationService.generateAllLocationsFile("csv");
        System.out.println(String.format("Getting all city id with state name %s, and city name %s.", "sc", "biguas"));
        assertEquals("mocked strategy", result);
    }
}