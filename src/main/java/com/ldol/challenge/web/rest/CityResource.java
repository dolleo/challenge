package com.ldol.challenge.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldol.challenge.domain.AreaResult;
import com.ldol.challenge.domain.City;
import com.ldol.challenge.service.AreaResultService;
import com.ldol.challenge.service.CityService;
import com.ldol.challenge.service.errors.CityNotFoundException;
import com.ldol.challenge.web.rest.dto.CityDTO;
import com.ldol.challenge.web.rest.dto.ResponseDTO;
import com.ldol.challenge.web.rest.mapper.CityMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CityResource {

    private final CityService cityService;

    private final AreaResultService areaResultService;

    private final CityMapper cityMapper;

    public CityResource(CityService cityService, AreaResultService areaResultService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.areaResultService = areaResultService;
        this.cityMapper = cityMapper;
    }

    @GetMapping("/cities-by-tag")
    public ResponseEntity<ResponseDTO> getCityByTag(@RequestParam String tag, @RequestParam String isActive) {

        Boolean isActiveFlag = Boolean.parseBoolean(isActive);
        List<CityDTO> cities = cityService.getWithTagAndActivity(tag, isActiveFlag)
                .stream()
                .map(cityMapper::toDTO)
                .collect(Collectors.toList());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCities(cities);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/distance")
    public ResponseEntity<ResponseDTO> getDistanceBetweenTwoCites(@RequestParam String from, @RequestParam String to) {

        ResponseDTO responseDTO = new ResponseDTO();

        Double distance;
        try {
            distance = cityService.computeDistance(from, to);
        } catch (CityNotFoundException e) {
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

        CityDTO fromCity = new CityDTO();
        fromCity.setGuid(from);
        CityDTO toCity = new CityDTO();
        toCity.setGuid(to);

        responseDTO.setFrom(fromCity);
        responseDTO.setTo(toCity);
        responseDTO.setUnit("km");
        responseDTO.setDistance(distance);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/area")
    public ResponseEntity<ResponseDTO> getCitiesWithinArea(@RequestParam String from, @RequestParam int distance) throws InterruptedException {

        ResponseDTO responseDTO = new ResponseDTO();

        try {
            cityService.searchCitiesWithinArea(from, distance, "2152f96f-50c7-4d76-9e18-f7033bd14428");
        } catch (CityNotFoundException e) {
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

        responseDTO.setResultsUrl("http://127.0.0.1:8080/area-result/2152f96f-50c7-4d76-9e18-f7033bd14428");
        return ResponseEntity.accepted().body(responseDTO);
    }

    @GetMapping("/area-result/{processId}")
    public ResponseEntity<ResponseDTO> getCitiesWithinAreaFinal(@PathVariable String processId) {

        AreaResult areaResult = areaResultService.findById(processId);
        ResponseDTO responseDTO = new ResponseDTO();

        if (areaResult != null) {
            responseDTO.setCities(areaResult.getCities().stream().map(cityMapper::toDTO).collect(Collectors.toList()));
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.accepted().body(responseDTO);
        }
    }

    @GetMapping("/all-cities")
    public ResponseEntity<StreamingResponseBody> streamAllCities() {

        List<City> allCities = cityService.findAllCities();
        ObjectMapper mapper = new ObjectMapper();

        StreamingResponseBody responseBody = response -> {
            String jsonStart = "[\n";
            response.write(jsonStart.getBytes());
            for (int i = 0; i < allCities.size(); i++) {
                CityDTO cityDTO = cityMapper.toDTO(allCities.get(i));
                String jsonString = i != allCities.size() - 1
                        ? mapper.writeValueAsString(cityDTO) + ",\n"
                        : mapper.writeValueAsString(cityDTO) + "\n";
                response.write(jsonString.getBytes());
                response.flush();
            }
            String jsonEnd = "]";
            response.write(jsonEnd.getBytes());
            response.flush();
        };

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(responseBody);
    }
}
