
package com.example.flightSearch.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

@Data
public class Segment {
    @NotNull(message = "Origin is required")
    @JsonProperty("Origin")
    private String origin;

    @NotNull(message = "Destination is required")
    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("FlightCabinClass")
    private int flightCabinClass;

    @JsonProperty("PreferredDepartureTime") // Date only (yyyy-MM-dd)
    private String preferredDepartureTime;

    @JsonProperty("PreferredArrivalTime")   // Date only (yyyy-MM-dd)
    private String preferredArrivalTime;

    @JsonProperty("DirectFlight")  // Keep this for the API
    private boolean isDirectFlight; // Changed to isDirectFlight (lowercase 'i')

    @JsonProperty("OneStopFlight") // Keep this for the API
    private boolean isOneStopFlight; // Changed to isOneStopFlight (lowercase 'i')

    // ... other fields if any
}