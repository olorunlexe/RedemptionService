package com.interswitch.redemptionapi.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;


@Data
public class Redemption {
    private long id;
    private long RedemptionId;
    private String code;
    private String merchantId;
    private Date datetime;
    private String voucherType;
    private long giftBalanceBeforeRedemption;
    private long giftAmountRedeemed;
    private long discountAmountRedeemed;
    private long discountUnitRedeemed;
    private float discountPercentRedeemed;
    private long valueAmountRedeemed;

    public Redemption(@JsonProperty("id") long id, @JsonProperty("code") String code, @JsonProperty("merchantId") String merchantId,@JsonProperty("datetime") Date datetime,@JsonProperty("voucherType") String voucherType,@JsonProperty("giftBalanceBeforeRedemption") long giftBalanceBeforeRedemption,@JsonProperty("giftAmountRedeemed") long giftAmountRedeemed,@JsonProperty("discountAmountRedeemed") long discountAmountRedeemed,@JsonProperty("discountUnitRedeemed") long discountUnitRedeemed,@JsonProperty("discountPercentRedeemed") float discountPercentRedeemed,@JsonProperty("valueAmountRedeemed") long valueAmountRedeemed) {
        this.id = id;
        this.code = code;
        this.merchantId = merchantId;
        this.datetime = datetime;
        this.voucherType = voucherType;
        this.giftBalanceBeforeRedemption = giftBalanceBeforeRedemption;
        this.giftAmountRedeemed = giftAmountRedeemed;
        this.discountAmountRedeemed = discountAmountRedeemed;
        this.discountUnitRedeemed = discountUnitRedeemed;
        this.discountPercentRedeemed = discountPercentRedeemed;
        this.valueAmountRedeemed = valueAmountRedeemed;
    }
}
