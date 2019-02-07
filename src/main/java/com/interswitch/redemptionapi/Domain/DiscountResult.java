package com.interswitch.redemptionapi.Domain;

import lombok.Data;

import java.sql.Date;

@Data
public class DiscountResult {
    private long id;
    private String code;
    private String merchantId;
    private Date datetime;
    private String voucherType;
    private Date expiryDate;
    private Date creationDate;
    private String metadata;
    private String description;
    private long discountAmount;
    private long discountUnit;
    private float discountPercent;
    //private long redemptionCount;
}
