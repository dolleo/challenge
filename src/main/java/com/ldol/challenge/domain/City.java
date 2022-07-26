package com.ldol.challenge.domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "city")
public class City {

    @Id
    private String guid;

    @Column(name = "is_active")
    @NotNull
    private boolean isActive;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "latitude")
    @NotNull
    private double latitude;

    @Column(name = "longitude")
    @NotNull
    private double longitude;

    @Column(name = "tag0")
    @NotNull
    private String tag0;

    @Column(name = "tag1")
    @NotNull
    private String tag1;

    @Column(name = "tag2")
    @NotNull
    private String tag2;

    @Column(name = "tag3")
    @NotNull
    private String tag3;

    @Column(name = "tag4")
    @NotNull
    private String tag4;

    @Column(name = "tag5")
    @NotNull
    private String tag5;

    @Column(name = "tag6")
    @NotNull
    private String tag6;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTag0() {
        return tag0;
    }

    public void setTag0(String tag0) {
        this.tag0 = tag0;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4() {
        return tag4;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5() {
        return tag5;
    }

    public void setTag5(String tag5) {
        this.tag5 = tag5;
    }

    public String getTag6() {
        return tag6;
    }

    public void setTag6(String tag6) {
        this.tag6 = tag6;
    }

    public List<String> getAllTags() {
        return Arrays.asList(this.tag0, this.tag1, this.tag2, this.tag3, this.tag4, this.tag5, this.tag6);
    }

    @Override
    public String toString() {
        return "City{" +
                "guid='" + guid + '\'' +
                ", isActive=" + isActive +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
