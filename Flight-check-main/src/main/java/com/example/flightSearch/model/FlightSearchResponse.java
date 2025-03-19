package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FlightSearchResponse {
    @JsonProperty("Response")
    private Response Response;
}