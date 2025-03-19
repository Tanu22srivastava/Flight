package com.example.flightSearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class FareClassification{
    @JsonProperty("Color")
    private String color;
    @JsonProperty("Type")
    private String type;
}