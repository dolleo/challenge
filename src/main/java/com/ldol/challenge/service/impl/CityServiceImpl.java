package com.ldol.challenge.service.impl;

import com.ldol.challenge.domain.AreaResult;
import com.ldol.challenge.domain.City;
import com.ldol.challenge.repository.AreaResultRepository;
import com.ldol.challenge.repository.CityRepository;
import com.ldol.challenge.service.AreaResultService;
import com.ldol.challenge.service.CityService;
import com.ldol.challenge.service.errors.CityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final int EARTH_RAYS = 6371000;

    private CityRepository cityRepository;

    private AreaResultService areaResultService;

    public CityServiceImpl(CityRepository cityRepository, AreaResultService areaResultService) {
        this.cityRepository = cityRepository;
        this.areaResultService = areaResultService;
    }

    @Override
    public List<City> getWithTagAndActivity(String tag, Boolean isActive) {
        return isActive != null ? cityRepository.findWithTagAndByIsActive(tag, isActive) : Collections.emptyList();
    }

    @Override
    public Double computeDistance(String from, String to) throws CityNotFoundException {
        Optional<City> cityFrom = cityRepository.findByGuid(from);
        Optional<City> cityTo = cityRepository.findByGuid(to);

        if (!cityFrom.isPresent()) {
            throw new CityNotFoundException(from);
        } else if (!cityTo.isPresent()) {
            throw new CityNotFoundException(to);
        } else {
            return calculateDistanceFromCoordinates(
                    cityFrom.get().getLatitude(),
                    cityFrom.get().getLongitude(),
                    cityTo.get().getLatitude(),
                    cityTo.get().getLongitude()
            );
        }
    }

    @Override
    @Async
    public void searchCitiesWithinArea(String from, int distance, String areaResultId) throws CityNotFoundException, InterruptedException {
//        Finding all cities within the perimeter is quite fast, so we can add the following line to demonstrate that asynchronicity works
//        Thread.sleep(5000);
        Optional<City> centerCity = cityRepository.findByGuid(from);
        if (centerCity.isPresent()) {
            AreaResult areaResult = new AreaResult();
            areaResult.setId(areaResultId);
            AtomicReference<Double> separation = new AtomicReference<>(0.0);
            List<City> result = cityRepository.findAll()
                    .stream()
                    .filter(city -> {
                        separation.set(calculateDistanceFromCoordinates(centerCity.get().getLatitude(),
                                centerCity.get().getLongitude(),
                                city.getLatitude(),
                                city.getLongitude()));
                        return separation.get() <= distance;
                    })
                    .filter(city -> !city.getGuid().equals(from))
                    .collect(Collectors.toList());
            areaResult.setCities(result);
            areaResultService.save(areaResult);
        } else {
            throw new CityNotFoundException(from);
        }
    }

    @Override
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    private Double calculateDistanceFromCoordinates(Double fromLatitude, Double fromLongitude, Double toLatitude, Double toLongitude) {
          double distanceInMeter = Math.cos(Math.toRadians(fromLatitude))
                * Math.cos(Math.toRadians(toLatitude))
                * Math.cos(Math.toRadians(toLongitude - fromLongitude))
                + Math.sin(Math.toRadians(fromLatitude))
                * Math.sin(Math.toRadians(toLatitude));
        return Math.round(Math.acos(distanceInMeter) * EARTH_RAYS / 10) / 100.0;
    }
}
