package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class FareRules {
    @JsonProperty("Origin")
    private String origin;

    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("Airline")
    private String airline;

    @JsonProperty("FareBasisCode")
    private String fareBasisCode;

    @JsonProperty("FareRuleDetail")
    private String fareRuleDetail;

    @JsonProperty("FareRestriction")
    private String fareRestriction;

    @JsonProperty("FareFamilyCode")
    private String fareFamilyCode;

    @JsonProperty("FareRuleIndex")
    private String fareRuleIndex;

    // ... other fields
}

// Similarly, create PenaltyCharges and MiniFareRules classes.