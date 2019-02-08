package com.interswitch.redemptionapi.Service.Impl;

import com.interswitch.redemptionapi.Domain.DiscountResult;
import com.interswitch.redemptionapi.Domain.Redemption;
import com.interswitch.redemptionapi.Service.IDiscountRedemptionService;
import com.interswitch.redemptionapi.Util.RedeemVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DiscountRedemptionService implements IDiscountRedemptionService {
    private static final Logger log = LoggerFactory.getLogger(DiscountRedemptionService.class);

    private RabbitTemplate rabbitTemplate;
    private RedemptionService redemptionService;

    public DiscountRedemptionService(RabbitTemplate rabbitTemplate, RedemptionService redemptionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.redemptionService = redemptionService;
    }

    @Override
    public void redeemDiscountVoucher(DiscountResult discountResult) {
        Redemption redemption = new Redemption();
        if (discountResult.getDiscountAmount() != 0) {
            redemption.setDiscountAmountRedeemed(discountResult.getDiscountAmount());
        } else if (discountResult.getDiscountPercent() != 0.0) {
            redemption.setDiscountPercentRedeemed(discountResult.getDiscountPercent());
        } else if (discountResult.getDiscountUnit() != 0) {
            redemption.setDiscountUnitRedeemed(discountResult.getDiscountUnit());
        }
        redemption.setCode(discountResult.getCode());
        redemption.setVoucherType(discountResult.getVoucherType());
        redemption.setMerchantId(discountResult.getMerchantId());
        redemptionService.createRedemption(redemption);
        log.info("Redemption Object Set!" + redemption.toString());
        rabbitTemplate.convertAndSend("gift-exchange", "gift-one", discountResult);
        log.info("Update published!" + discountResult.toString());
    }
}
