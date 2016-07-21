package com.comments.domain;

/**
 * Created by joshua on 2016-07-14.
 */
public class Location {
    private String name;
    private String countryName;
    private Double latitude;
    private Double longitude;

    public Location(String name, String countryName, Double latitude, Double longitude) {
        this.name = name;
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
