package com.interswitch.redemptionapi.Service;

import com.interswitch.redemptionapi.Domain.GiftResult;
import com.interswitch.redemptionapi.Domain.Redemption;

public interface IGiftRedemptionService {
    void redeemGiftVoucher(GiftResult receiveGift, Redemption redemption);
}
