package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class TaxBreakup {  // New class for TaxBreakup
    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private double value;
}