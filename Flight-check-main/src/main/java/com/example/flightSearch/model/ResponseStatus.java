package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class ResponseStatus { // Example: If ResponseStatus is an object
    @JsonProperty("Status")
    private String status;
    // ... other fields within ResponseStatus if any
}