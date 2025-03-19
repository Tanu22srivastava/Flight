package com.example.flightSearch.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data; // Using Lombok for getters and setters
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class FlightSearchRequest {
    @JsonProperty("EndUserIp")
    private String EndUserIp;
    @JsonProperty("TokenId")
    private String TokenId;
    @JsonProperty("AdultCount")
    private int AdultCount;
    @JsonProperty("ChildCount")
    private int ChildCount;
    @JsonProperty("InfantCount")
    private int InfantCount;
    @JsonProperty("DirectFlight")
    private boolean DirectFlight = true;
    @JsonProperty("OneStopFlight")
    private boolean OneStopFlight;
    @JsonProperty("JourneyType")
    private int JourneyType;

    private String PreferredAirlines;
    @JsonProperty("Segments")
    private List<Segment> Segments;
    private String Sources;
}