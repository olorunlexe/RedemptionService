package com.interswitch.redemptionapi.Service;

import com.interswitch.redemptionapi.Domain.GiftResult;
import com.interswitch.redemptionapi.Domain.Redemption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class GiftRedemptionService {
    private static final Logger log = LoggerFactory.getLogger(GiftRedemptionService.class);
    private RabbitTemplate rabbitTemplate;
    private RedemptionService redemptionService;

    public GiftRedemptionService(RabbitTemplate rabbitTemplate, RedemptionService redemptionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.redemptionService = redemptionService;
    }

    public void redeemGiftVoucher(GiftResult receiveGift, Redemption redemption) {
        // Use Amount and GiftBalance to make necessary deductions and
        // the Insert into Redemption table or Update the existing value if still valid

        redemption.setGiftBalanceBeforeRedemption(receiveGift.getGiftBalance());
        redemption.setVoucherType(receiveGift.getVoucherType());
        redemption.setMerchantId(receiveGift.getMerchantId());
        log.info("Creating redemption" + redemption.toString());
        redemptionService.createRedemption(redemption);

        long newBalanceForVoucherUpdate = redemption.getGiftBalanceBeforeRedemption() - redemption.getGiftAmountRedeemed();
        receiveGift.setGiftBalance(newBalanceForVoucherUpdate);
        rabbitTemplate.convertAndSend("gift-exchange", "gift-two", receiveGift);
    }
}
