package com.ldol.challenge.service;

import com.ldol.challenge.domain.City;
import com.ldol.challenge.repository.CityRepository;
import com.ldol.challenge.service.errors.CityNotFoundException;
import com.ldol.challenge.service.impl.CityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void testComputeDistance() throws CityNotFoundException {
        // GIVEN
        String from = "fromCityGuid";
        String to = "toCityGuid";

        City cityFrom = new CityBuilder()
                .withGuid(from)
                .build();
        City cityTo = new CityBuilder()
                .withGuid(to)
                .withLatitude(90 / Math.PI)
                .build();

        double expectedResult = 3185.5; // Equals Earth ray divided by 2000

        when(cityRepository.findByGuid(from)).thenReturn(Optional.ofNullable(cityFrom));
        when(cityRepository.findByGuid(to)).thenReturn(Optional.ofNullable(cityTo));

        // WHEN
        Double result = cityService.computeDistance(from, to);

        // THEN
        assertEquals(expectedResult, result);
    }

    @Test
    void computeDistanceShouldThrowException_WhenToCityNotFound() {
        // GIVEN
        String from = "fromCityGuid";
        String to = "toCityGuid";

        City cityFrom = new CityBuilder()
                .withGuid(from)
                .build();

        when(cityRepository.findByGuid(from)).thenReturn(Optional.ofNullable(cityFrom));

        // WHEN
        when(cityRepository.findByGuid(to)).thenReturn(Optional.empty());

        // THEN
        assertThrows(CityNotFoundException.class, () -> cityService.computeDistance(from, to));
    }
}

class CityBuilder {

    private String guid;

    private boolean isActive = true;

    private String address = "dummyAddress";

    private double latitude = 0.0;

    private double longitude = 0.0;

    public CityBuilder() {}

    public City build() {
        City city = new City();

        city.setGuid(this.guid);
        city.setActive(this.isActive);
        city.setAddress(this.address);
        city.setLatitude(this.latitude);
        city.setLongitude(this.longitude);

        return city;
    }

    public CityBuilder withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public CityBuilder withActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public CityBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public CityBuilder withLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public CityBuilder withLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
