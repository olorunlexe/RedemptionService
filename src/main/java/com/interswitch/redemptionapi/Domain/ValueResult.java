package com.interswitch.redemptionapi.Domain;

import lombok.Data;

import java.sql.Date;

@Data
public class ValueResult {
    private long id;
    private String code;
    private String merchantId;
    private Date datetime;
    private String voucherType;
    private Date expiryDate;
    private Date creationDate;
    private String metadata;
    private String description;
    private long valueAmount;
}
