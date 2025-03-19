package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
// @JsonIgnoreProperties(ignoreUnknown = true)
public class MiniFareRules {
    @JsonProperty("JourneyPoints")
    private String journeyPoints;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("From")
    private String from;

    @JsonProperty("To")
    private String to;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("Details")
    private String details;

    @JsonProperty("OnlineReissueAllowed")
    private boolean onlineReissueAllowed; 

    @JsonProperty("OnlineRefundAllowed")
    private boolean onlineRefundAllowed;

    // ... other fields if any
}