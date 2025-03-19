package com.example.flightSearch.model;

import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
@Data
public
class FlightDetails {
  //This is a simplified version of the FlightDetails class.  You'll want to add
  //all the fields from the JSON response that you need.
    // private String ResultIndex;
    // private int Source;
    // private boolean IsLCC;
    // private boolean IsRefundable;
    // private Fare Fare;
    // private List<FareBreakdown> FareBreakdown;

    @JsonProperty("Segments")
    private List<List<FlightSegment>> segments; 

    @JsonProperty("FareBreakdown")
    private List<FareBreakdown> fareBreakdown;

    @JsonProperty("Fare")
    private Fare fare;
    @JsonProperty("FareCombinationId")
    private String FareCombinationId;

    @JsonProperty("FareRules")
    private List<FareRules> fareRules;
    //... other fields

    @JsonProperty("MiniFareRules")
    private List<List<MiniFareRules>> miniFareRules; // List of Lists

    @JsonProperty("PenaltyCharges")
    private PenaltyCharges penaltyCharges;

    // In FlightDetails.java
    @JsonProperty("FareInclusions")
    private List<String> FareInclusions;

// ... getters and setters

    // ... other fields (FirstNameFormat, IsBookableIfSeatNotAvailable, etc.)
    @JsonProperty("FirstNameFormat")
    private String firstNameFormat;
    @JsonProperty("IsBookableIfSeatNotAvailable")
    private boolean isBookableIfSeatNotAvailable;
    @JsonProperty("IsHoldAllowedWithSSR")
    private boolean isHoldAllowedWithSSR;
    @JsonProperty("IsUpsellAllowed")
    private boolean isUpsellAllowed;
    @JsonProperty("LastNameFormat")
    private String lastNameFormat;
    @JsonProperty("ResultIndex")
    private String resultIndex;
    @JsonProperty("Source")
    private int source;
    @JsonProperty("IsLCC")
    private boolean isLCC;
    @JsonProperty("IsRefundable")
    private boolean isRefundable;
    @JsonProperty("IsPanRequiredAtBook")
    private boolean isPanRequiredAtBook;
    @JsonProperty("IsPanRequiredAtTicket")
    private boolean isPanRequiredAtTicket;
    @JsonProperty("IsPassportRequiredAtBook")
    private boolean isPassportRequiredAtBook;
    @JsonProperty("IsPassportRequiredAtTicket")
    private boolean isPassportRequiredAtTicket;
    @JsonProperty("GSTAllowed")
    private boolean gstAllowed;
    @JsonProperty("IsCouponAppilcable")
    private boolean isCouponAppilcable;
    @JsonProperty("IsGSTMandatory")
    private boolean isGSTMandatory;
    @JsonProperty("AirlineRemark")
    private String airlineRemark;
    @JsonProperty("IsPassportFullDetailRequiredAtBook")
    private boolean isPassportFullDetailRequiredAtBook;
    @JsonProperty("ResultFareType")
    private String resultFareType;
    @JsonProperty("LastTicketDate")
    private String lastTicketDate;
    @JsonProperty("TicketAdvisory")
    private String ticketAdvisory;
    @JsonProperty("AirlineCode")
    private String airlineCode;
    @JsonProperty("ValidatingAirline")
    private String validatingAirline;
    @JsonProperty("FareClassification")
    private FareClassification fareClassification;
    @JsonProperty("FareInclusions")
    private List<FareInclusion> fareInclusions; 

    @JsonProperty("IsHoldMandatoryWithSSR")
    private boolean isHoldMandatoryWithSSR; // Or B

    @JsonProperty("IsFreeMealAvailable")
    private boolean isFreeMealAvailable;
}