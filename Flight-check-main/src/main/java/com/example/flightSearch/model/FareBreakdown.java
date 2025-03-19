package com.example.flightSearch.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public
class FareBreakdown {
    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("PassengerType")
    private int passengerType; // Or String, depending on your JSON

    @JsonProperty("PassengerCount")
    private int passengerCount;

    @JsonProperty("BaseFare")
    private double baseFare;

    @JsonProperty("Tax")
    private double tax;

    @JsonProperty("TaxBreakUp")
    private List<TaxBreakup> taxBreakUp;

    @JsonProperty("YQTax")
    private double yQTax;

    @JsonProperty("AdditionalTxnFeeOfrd")
    private double additionalTxnFeeOfrd;

    @JsonProperty("AdditionalTxnFeePub")
    private double additionalTxnFeePub;

    @JsonProperty("PGCharge")
    private double pgCharge;

    @JsonProperty("SupplierReissueCharges")
    private double supplierReissueCharges;
    // ... other fare breakdown details
    public String getOrigin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrigin'");
    }
    public String getDestination() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDestination'");
    }
}