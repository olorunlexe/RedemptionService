package com.interswitch.redemptionapi.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoucherRequest implements Serializable {

    private String VoucherType;
    private long ValueAmount;
    private long DiscountAmount;
    private int DiscountUnit;
    private int DiscountPercent;
    private long GiftAmount;
    private String Prefix;
    private String Suffix;
    private String CodePattern;
    private int CodeLength;
    private String CharacterSet;
    private String Separator;
    private String CreationDate;
    private String ExpiryDate;
    private String Description;
    private String Metadata;
    private int NumbersOfVoucherToCreate;
    private String MerchantId;


    public VoucherRequest(@JsonProperty("voucherType") String voucherType, @JsonProperty("valueAmount") long valueAmount, @JsonProperty("discountAmount") long discountAmount,
                          @JsonProperty("discountUnit") int discountUnit, @JsonProperty("discountPercent") int discountPercent, @JsonProperty("giftAmount") long giftAmount, @JsonProperty("prefix") String prefix,
                          @JsonProperty("suffix") String suffix, @JsonProperty("codePattern") String codePattern, @JsonProperty("codeLength") int codeLength, @JsonProperty("characterSet") String characterSet,
                          @JsonProperty("separator") String separator, @JsonProperty("creationDate") String creationDate, @JsonProperty("expiryDate") String expiryDate,
                          @JsonProperty("description") String description, @JsonProperty("metadata") String metadata, @JsonProperty("numbersOfVoucherToCreate") int numbersOfVoucherToCreate, @JsonProperty("merchantId") String merchantId) {
        VoucherType = voucherType;
        ValueAmount = valueAmount;
        DiscountAmount = discountAmount;
        DiscountUnit = discountUnit;
        DiscountPercent = discountPercent;
        GiftAmount = giftAmount;
        Prefix = prefix;
        Suffix = suffix;
        CodePattern = codePattern;
        CodeLength = codeLength;
        CharacterSet = characterSet;
        Separator = separator;
        CreationDate = creationDate;
        ExpiryDate = expiryDate;
        Description = description;
        Metadata = metadata;
        NumbersOfVoucherToCreate = numbersOfVoucherToCreate;
        MerchantId = merchantId;
    }
}
