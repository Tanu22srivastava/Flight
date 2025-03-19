package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class Error {
    @JsonProperty("ErrorCode") // Add this line!
    private int errorCode; // Or String if it's a string

    @JsonProperty("ErrorMessage")
    private String errorMessage;
}
