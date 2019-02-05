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

    public void redeemGiftVoucher(long amount, GiftResult receiveGift, Redemption redemption, RabbitTemplate rabbitTemplate, IRedemptionService redemptionService) {
        // Use Amount and GiftBalance to make necessary deductions and
        // the Insert into Redemption table or Update the existing value if still valid

        redemption.setGiftBalanceBeforeRedemption(receiveGift.getGiftBalance());
        redemption.setGiftAmountRedeemed(amount);
        redemption.setVoucherType(receiveGift.getVoucherType());
        redemption.setMerchantId(receiveGift.getMerchantId());
        log.info("Creating redemption" + redemption.toString());
        redemptionService.createRedemption(redemption);

        long newBalanceForVoucherUpdate = redemption.getGiftBalanceBeforeRedemption() - redemption.getGiftAmountRedeemed();
        receiveGift.setGiftBalance(newBalanceForVoucherUpdate);
        rabbitTemplate.convertAndSend("gift-exchange", "gift-three", receiveGift);
    }
}
