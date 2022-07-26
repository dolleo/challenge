package com.ldol.challenge.web.rest.dto;

import com.ldol.challenge.domain.City;

import java.util.List;

public class ResponseDTO {

    private List<CityDTO> cities;

    private CityDTO from;

    private CityDTO to;

    private String unit;

    private double distance;

    private String resultsUrl;

    private String errorMessage;

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }

    public CityDTO getFrom() {
        return from;
    }

    public void setFrom(CityDTO from) {
        this.from = from;
    }

    public CityDTO getTo() {
        return to;
    }

    public void setTo(CityDTO to) {
        this.to = to;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getResultsUrl() {
        return resultsUrl;
    }

    public void setResultsUrl(String resultsUrl) {
        this.resultsUrl = resultsUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
