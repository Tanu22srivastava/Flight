package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class ChargeBU {  // New class for ChargeBU
    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private double value;
}
