package com.interswitch.redemptionapi.Service.Impl;

import com.interswitch.redemptionapi.Domain.Redemption;
import com.interswitch.redemptionapi.Repository.IRedemptionRepository;
import com.interswitch.redemptionapi.Service.IRedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RedemptionService implements IRedemptionService {

    @Autowired
    IRedemptionRepository redemptionRepository;

    @Override
    public Redemption createRedemption(Redemption redemption) {
        return this.redemptionRepository.insert(redemption);
    }

    @Override
    public Redemption updateRedemption(Redemption redemption) {
        return null;
    }

    @Override
    public Redemption readRedemptionByCode(String code) {
        return this.redemptionRepository.findByCode(code);
    }

    @Override
    public Redemption readRedemptionByDate(Date date) {
        return this.redemptionRepository.findByDate(date);
    }
}
