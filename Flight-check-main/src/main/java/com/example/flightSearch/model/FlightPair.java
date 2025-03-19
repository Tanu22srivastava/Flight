package com.example.flightSearch.model;

public class FlightPair {
    private FlightSearchResult outboundFlight;
    private FlightSearchResult returnFlight;
    

    // Constructor
    public FlightPair(FlightSearchResult outboundFlight, FlightSearchResult returnFlight) {
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
    }

    // Getters
    public FlightSearchResult getOutboundFlight() {
        return outboundFlight;
    }

    public FlightSearchResult getReturnFlight() {
        return returnFlight;
    }

    // Setters (if needed)
    public void setOutboundFlight(FlightSearchResult outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    public void setReturnFlight(FlightSearchResult returnFlight) {
        this.returnFlight = returnFlight;
    }
}
