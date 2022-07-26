package com.ldol.challenge.service;

import com.ldol.challenge.domain.City;
import com.ldol.challenge.service.errors.CityNotFoundException;

import java.util.List;

public interface CityService {

    List<City> getWithTagAndActivity(String tag, Boolean isActive);

    Double computeDistance(String from, String to) throws CityNotFoundException;

    void searchCitiesWithinArea(String from, int distance, String areaResultId) throws InterruptedException, CityNotFoundException;

    List<City> findAllCities();
}
