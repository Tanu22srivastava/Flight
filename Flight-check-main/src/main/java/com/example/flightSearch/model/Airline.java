package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Airline {
    @JsonProperty("FlightNumber")
    private String flightNumber;

    @JsonProperty("AirlineName")
    private String airlineName;
    @JsonProperty("AirlineCode")  // Add this line
    private String airlineCode;   // Add this line

    @JsonProperty("FareClass")
    private String fareClass;

    @JsonProperty("OperatingCarrier")
    private String operatingCarrier;


    // ... other fields if any
}
