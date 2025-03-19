package com.example.flightSearch.model;

import lombok.Data;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
@Data
public
class Response {
    @JsonProperty("TraceId") // Use @JsonProperty for all fields (best practice)
    private String traceId;

    @JsonProperty("ResponseStatus") // Add this line!
    private int responseStatus; // Or whatever the correct type is

    @JsonProperty("Results")
    private List<List<FlightDetails>> results;

    @JsonProperty("Error") // If there is error object in json response
    private Error error;

    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("Origin")
    private String origin;

    @JsonProperty("ResultRecommendationType")
    private String resultRecommendationType;
}