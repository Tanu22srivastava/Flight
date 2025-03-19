package com.example.flightSearch.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Origin {
    @JsonProperty("Airport")
    private Airport airport;

    @JsonProperty("CityCode")
    private String cityCode;

    @JsonProperty("DepTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String depTime; // Changed to LocalDateTime

    public void printDepTime() {
        System.out.println("DepTime: haan time  " + (depTime != null ? depTime.toString() : "null"));
    }
    
    

    // ... other fields if any
}