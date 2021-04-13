package com.example.nationalinformation;

import java.io.Serializable;

public class CountryEntity implements Serializable {
    private String countryCode;
    private String name;
    private float population;
    private float acreage;

    public CountryEntity(String countryCode, String name, float population, float acreage) {
        this.countryCode = countryCode;
        this.name = name;
        this.population = population;
        this.acreage = acreage;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public float getAcreage() {
        return acreage;
    }

    public float getPopulation() {
        return population;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setName(String countryName) {
        this.name = name;
    }

    public void setPopulation(float population) {
        this.population = population;
    }

    public void setAcreage(float acreage) {
        this.acreage = acreage;
    }
}
