package com.example.flightSearch.model;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class FlightSegment {

    // @JsonProperty("Airline")
    // private Airline airline;

    // @JsonProperty("Origin")
    // private Origin origin;

    // @JsonProperty("Destination")
    // private Destination destination;

    // @JsonProperty("Duration")
    // private int duration;
    @JsonProperty("MiniFareRules")
    private MiniFareRules miniFareRules;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("DepTime") // Correctly named property
    private Origin depTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("ArrTime") // Correctly named property
    private String arrTime;
    @JsonProperty("Segments")
    private List<List<FlightSegment>> segments;
    
    @JsonProperty("FareBreakdown")
    private List<FareBreakdown> fareBreakdown;
    // // ... other fields from the response
    @JsonProperty("Origin")
    private Origin origin; // Object

    @JsonProperty("Destination")
    private Destination destination; // Object

    @JsonProperty("AccumulatedDuration")  // Add this line
    private int accumulatedDuration; 

    

    // ... other fields (Baggage, CabinBaggage, etc.)
    @JsonProperty("Baggage")
    private String baggage;
    @JsonProperty("CabinBaggage")
    private String cabinBaggage;
    @JsonProperty("CabinClass")
    private int cabinClass;
    @JsonProperty("SupplierFareClass")
    private String supplierFareClass;
    @JsonProperty("TripIndicator")
    private int tripIndicator;
    @JsonProperty("SegmentIndicator")
    private int segmentIndicator;
    @JsonProperty("Airline")
    private Airline airline;
    @JsonProperty("NoOfSeatAvailable")
    private int noOfSeatAvailable;
    @JsonProperty("Duration")
    private int duration;
    @JsonProperty("GroundTime")
    private int groundTime;
    @JsonProperty("Mile")
    private int mile;
    @JsonProperty("StopOver")
    private boolean stopOver;
    @JsonProperty("FlightInfoIndex")
    private String flightInfoIndex;
    @JsonProperty("StopPoint")
    private String stopPoint;
    @JsonProperty("StopPointArrivalTime")
    private String stopPointArrivalTime;
    @JsonProperty("StopPointDepartureTime")
    private String stopPointDepartureTime;
    @JsonProperty("Craft")
    private String craft;
    @JsonProperty("Remark")
    private String remark;
    @JsonProperty("IsETicketEligible")
    private boolean isETicketEligible;
    @JsonProperty("FlightStatus")
    private String flightStatus;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("FareClassification")
    private FareClassification fareClassification;

    
}

// Airline, Origin, Destination, Airport classes (as defined previously)