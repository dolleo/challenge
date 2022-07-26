package com.ldol.challenge.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "area_result")
public class AreaResult {

    @Id
    private String id;

    @ManyToMany
    @JoinTable(
            name = "area_result_cities",
            joinColumns = @JoinColumn(name = "area_result_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_guid", referencedColumnName = "guid")
    )
    private List<City> cities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "AreaResult{" +
                "id='" + id + '\'' +
                ", cities=" + cities +
                '}';
    }
}
