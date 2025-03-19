package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Airport {
    @JsonProperty("AirportCode")
    private String airportCode;
    @JsonProperty("AirportName")
    private String airportName;

    @JsonProperty("Terminal")  // Add this line
    private String terminal;   // Add this line

    @JsonProperty("CityCode")  // Add this line
    private String cityCode;   // Add this line

    @JsonProperty("CityName")  // Add this line
    private String cityName;   // Add this line

    @JsonProperty("CountryCode")  // Add this line
    private String countryCode;   // Add this line

    @JsonProperty("CountryName")  // Add this line
    private String countryName;   // Add this line


    // ... other fields if any
}
