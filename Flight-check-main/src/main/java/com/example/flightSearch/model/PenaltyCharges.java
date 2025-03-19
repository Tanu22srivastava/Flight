package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class PenaltyCharges {
    @JsonProperty("ReissueCharge")
    private String reissueCharge;

    @JsonProperty("CancellationCharge")
    private String cancellationCharge;

    // ... other fields if any
}