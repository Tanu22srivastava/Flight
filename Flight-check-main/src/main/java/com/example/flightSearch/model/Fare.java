package com.example.flightSearch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public
class Fare {
    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("BaseFare")
    private double baseFare;

    @JsonProperty("Tax")
    private double tax;

    @JsonProperty("TaxBreakup")
    private List<TaxBreakup> taxBreakup; // Add this for tax breakup

    @JsonProperty("YQTax")
    private double yQTax;

    @JsonProperty("AdditionalTxnFeeOfrd")
    private double additionalTxnFeeOfrd;

    @JsonProperty("AdditionalTxnFeePub")
    private double additionalTxnFeePub;

    @JsonProperty("PGCharge")
    private double pgCharge;

    @JsonProperty("OtherCharges")
    private double otherCharges;

    @JsonProperty("ChargeBU")
    private List<ChargeBU> chargeBU; // Important: This is a list!

    @JsonProperty("Discount")
    private double discount;

    @JsonProperty("PublishedFare")
    private double publishedFare;

    @JsonProperty("CommissionEarned")
    private double commissionEarned;

    @JsonProperty("PLBEarned")
    private double plbEarned;

    @JsonProperty("IncentiveEarned")
    private double incentiveEarned;

    @JsonProperty("OfferedFare")
    private double offeredFare;

    @JsonProperty("TdsOnCommission")
    private double tdsOnCommission;

    @JsonProperty("TdsOnPLB")
    private double tdsOnPLB;

    @JsonProperty("TdsOnIncentive")
    private double tdsOnIncentive;

    @JsonProperty("ServiceFee")
    private double serviceFee;

    @JsonProperty("TotalBaggageCharges")
    private double totalBaggageCharges;

    @JsonProperty("TotalMealCharges")
    private double totalMealCharges;

    @JsonProperty("TotalSeatCharges")
    private double totalSeatCharges;

    @JsonProperty("TotalSpecialServiceCharges")
    private double totalSpecialServiceCharges;

    @JsonProperty("ServiceFeeDisplayType")
    private String ServiceFeeDisplayType;

    // ... other fare details
}
