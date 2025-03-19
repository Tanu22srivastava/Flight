package com.example.flightSearch.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Destination {
    @JsonProperty("Airport")
    private Airport airport;

    @JsonProperty("CityCode")
    private String cityCode;

    @JsonProperty("ArrTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String arrTime; // Changed to LocalDateTime

    // ... other fields if any
}