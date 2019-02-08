package com.interswitch.redemptionapi.Domain;

import lombok.Data;


@Data
public class Redemption {
    private long id;
    private long RedemptionId;
    private String code;
    private String merchantId;
    private String voucherType;
    private long giftBalanceBeforeRedemption;
    private long giftAmountRedeemed;
    private long discountAmountRedeemed;
    private long discountUnitRedeemed;
    private float discountPercentRedeemed;
    private long valueAmountRedeemed;
}
