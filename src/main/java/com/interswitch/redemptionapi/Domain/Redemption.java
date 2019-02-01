package com.interswitch.redemptionapi.Domain;

import lombok.Data;

import java.sql.Date;


@Data
public class Redemption {
    private long id;
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
}
