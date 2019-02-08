package com.interswitch.redemptionapi.Util;

import com.interswitch.redemptionapi.Domain.DiscountResult;
import com.interswitch.redemptionapi.Domain.GiftResult;
import com.interswitch.redemptionapi.Domain.Redemption;
import com.interswitch.redemptionapi.Domain.ValueResult;
import com.interswitch.redemptionapi.Service.IDiscountRedemptionService;
import com.interswitch.redemptionapi.Service.IGiftRedemptionService;
import com.interswitch.redemptionapi.Service.IRedemptionService;
import com.interswitch.redemptionapi.Service.IValueRedemptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


@Component
public class RedeemVoucher {
    private static final Logger log = LoggerFactory.getLogger(RedeemVoucher.class);

    @Autowired
    private IGiftRedemptionService giftRedemptionService;
    @Autowired
    private IDiscountRedemptionService IDiscountRedemptionService;
    @Autowired
    private IValueRedemptionService valueRedemptionService;
    @Autowired
    private IRedemptionService redemptionService;
    @Autowired
    private RestTemplate restTemplate;

    public boolean redeemGiftVoucher(@Validated @RequestBody Redemption redemption) {
        final String uri = "http://localhost:63745/api/v1/gift/" + redemption.getCode();
        log.debug("Rest Api call to ..." + uri);
        GiftResult receiveGift = restTemplate.getForObject(uri, GiftResult.class);
        log.debug("Gift Voucher Object received ..." + receiveGift);
        if (receiveGift != null && receiveGift.getGiftBalance() >= redemption.getGiftAmountRedeemed()) {
            giftRedemptionService.redeemGiftVoucher(receiveGift, redemption);
            log.info("Gift voucher redeemed" + redemption.getCode());
            return true;
        }
        log.error("Gift Voucher redemption not successful");
        return false;
    }

    public boolean redeemDiscountVoucher(@Validated @RequestBody String code) {
        final String uri = "http://localhost:63745/api/v1/discount/" + code;
        log.debug("Rest Api call to ..." + uri);
        DiscountResult discountResult = restTemplate.getForObject(uri, DiscountResult.class);
        if (discountResult != null) {
            IDiscountRedemptionService.redeemDiscountVoucher(discountResult);
            log.info("Discount voucher redeemed" + discountResult.getCode());
            return true;
        }
        log.error("Discount Voucher redemption not successful");
        return false;
    }

    public boolean redeemValueVoucher(@Validated @RequestBody String code) {
        Redemption checkRedemptionTableForCode = redemptionService.readRedemptionByCode(code);
        if (checkRedemptionTableForCode != null) {
            return false;
        } else {
            final String uri = "http://localhost:63745/api/v1/value/" + code;
            log.debug("Rest Api call to ..." + uri);
            ValueResult valueResult = restTemplate.getForObject(uri, ValueResult.class);
            if (valueResult != null) {
                valueRedemptionService.redeemValueVoucher(valueResult);
                log.info("Discount voucher redeemed" + valueResult.getCode());
                return true;
            }
            log.error("Value Voucher redemption not successful");
            return false;
        }
    }
}
