package com.interswitch.redemptionapi.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiftResult implements Serializable {
    private long id;
    private String Code;
    private String MerchantId;
    private String VoucherStatus;
    private Date ExpiryDate;
    private Date CreationDate;
    private String VoucherType;
    private String Metadata;
    private String Description;
    private long giftBalance;
    private long giftAmount;
}
