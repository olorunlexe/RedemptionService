package com.interswitch.redemptionapi.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiftResult implements Serializable {
    private long id;
    private String code;
    private String merchantId;
    private String voucherStatus;
    private Date expiryDate;
    private Date creationDate;
    private String voucherType;
    private String metadata;
    private String description;
    private long giftBalance;
    private long giftAmount;
}
