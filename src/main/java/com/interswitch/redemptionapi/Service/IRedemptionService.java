package com.interswitch.redemptionapi.Service;

import com.interswitch.redemptionapi.Domain.Redemption;

import java.util.Date;


public interface IRedemptionService {
    Redemption createRedemption(Redemption redemption);

    Redemption updateRedemption(Redemption redemption);

    Redemption readRedemptionByCode(String code);

    Redemption readRedemptionByDate(Date date);
}
