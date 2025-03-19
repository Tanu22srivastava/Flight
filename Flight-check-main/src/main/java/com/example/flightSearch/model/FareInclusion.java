package com.example.flightSearch.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
public class FareInclusion {
    private String type;
    private String description;
    // ... other fields
}