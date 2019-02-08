package com.interswitch.redemptionapi.Service.Impl;

import com.interswitch.redemptionapi.Domain.Redemption;
import com.interswitch.redemptionapi.Domain.ValueResult;
import com.interswitch.redemptionapi.Service.IValueRedemptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ValueRedemptionService implements IValueRedemptionService {
    private static final Logger log = LoggerFactory.getLogger(ValueRedemptionService.class);    public Redemption redemption;
    private RabbitTemplate rabbitTemplate;
    private RedemptionService redemptionService;

    public ValueRedemptionService(RabbitTemplate rabbitTemplate, RedemptionService redemptionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.redemptionService = redemptionService;
    }

    @Override
    public void redeemValueVoucher(ValueResult valueResult) {
        Redemption redemption = new Redemption();
        redemption.setValueAmountRedeemed(valueResult.getValueAmount());
        redemption.setVoucherType(valueResult.getVoucherType());
        redemption.setMerchantId(valueResult.getMerchantId());
        redemption.setCode(valueResult.getCode());
        redemptionService.createRedemption(redemption);
        log.info("Creating redemption" + redemption.toString());


        rabbitTemplate.convertAndSend("gift-exchange", "gift-three", valueResult);
        log.info("Redeemed ValueObject published" + redemption.toString());

    }
}
