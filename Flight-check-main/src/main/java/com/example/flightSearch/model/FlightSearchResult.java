package com.example.flightSearch.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FlightSearchResult {
    @JsonProperty("Origin")
    private String origin;
    @JsonProperty("Origin")
    private String oneStopAirport;
    @JsonProperty("Destination")
    private String destination;
    @JsonProperty("FlightNumber")
    private String flightNumber;
    @JsonProperty("DepTime")
    private LocalDateTime depTime; // Departure Time
    @JsonProperty("ArrTime")
    private LocalDateTime arrTime; // Arrival Time
    @JsonProperty("Duration")
    private int duration; // Duration
    @JsonProperty("AirlineName")
    private String airlineName;
    @JsonProperty("PublishedFare")
    private String publishedFare;
    @JsonProperty("BaseFare")
    private String baseFare;
    @JsonProperty("OfferedFare")
    private String offeredFare;

    @JsonProperty("IsRefundable")
    private boolean isRefundable;
    @JsonProperty("ResultFareType")
    private String resultFareType;

    @JsonProperty("IsFreeMealAvailable")
    private boolean isFreeMealAvailable;
    @JsonProperty("AirlineRemark")
    private String airlineRemark;

    @JsonProperty("AirlineCode") // Or whatever name the API uses
    private String airlineCode; // Add this line!
    @JsonProperty("Terminal") // Or whatever name the API uses
    private String originTerminal;
    @JsonProperty("TripIndicator") // Or whatever name the API uses
    private int tripIndicator;
    @JsonProperty("SupplierFareClass")
    private String supplierFareClass;
    @JsonProperty("NoOfSeatAvailable")
    private int noOfSeatAvailable;
    @JsonProperty("Terminal") // Or whatever name the API uses
    private String destinationTerminal;
    @JsonProperty("FareBreakdown")  // Add this line!
    private List<FareBreakdown> fareBreakdown; // Add this line!

    // ... other fields you want to display
}