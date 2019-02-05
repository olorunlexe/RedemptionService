package com.interswitch.redemptionapi.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiftResult {
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

    public GiftResult(@JsonProperty("id")long id,@JsonProperty("code") String code,@JsonProperty("merchantId") String merchantId,@JsonProperty("voucherStatus") String voucherStatus,@JsonProperty("expiryDate") Date expiryDate,@JsonProperty("creationDate") Date creationDate,@JsonProperty("voucherType") String voucherType,@JsonProperty("metadata") String metadata,@JsonProperty("description") String description,@JsonProperty("giftBalance") long giftBalance,@JsonProperty("giftAmount") long giftAmount) {
        this.id = id;
        Code = code;
        MerchantId = merchantId;
        VoucherStatus = voucherStatus;
        ExpiryDate = expiryDate;
        CreationDate = creationDate;
        VoucherType = voucherType;
        Metadata = metadata;
        Description = description;
        this.giftBalance = giftBalance;
        this.giftAmount = giftAmount;
    }
}
