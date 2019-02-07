package com.interswitch.redemptionapi.Controller;


import com.interswitch.redemptionapi.Domain.Redemption;
import com.interswitch.redemptionapi.Service.GiftRedemptionService;
import com.interswitch.redemptionapi.Service.IRedemptionService;
import com.interswitch.redemptionapi.VoucherReceiver;
import com.interswitch.redemptionapi.config.RedeemVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/api/v1")
public class RedemptionController {


    private static final Logger log = LoggerFactory.getLogger(RedemptionController.class);
    private IRedemptionService redemptionService;
    private RabbitTemplate rabbitTemplate;
    private VoucherReceiver voucherReceiver;
    private RedeemVoucher redeemVoucher;

    public RedemptionController(IRedemptionService redemptionService, RabbitTemplate rabbitTemplate, VoucherReceiver voucherReceiver, GiftRedemptionService giftRedemptionService, RedeemVoucher redeemVoucher) {
        this.redemptionService = redemptionService;
        this.rabbitTemplate = rabbitTemplate;
        this.voucherReceiver = voucherReceiver;
        this.redeemVoucher = redeemVoucher;
    }

    @PostMapping(path = "/gift", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String insertGiftRedemption(@RequestBody @Validated final Redemption redemption) throws IOException {
        if (redeemVoucher.redeemGiftVoucher(redemption)) return "Gift Voucher redeemed Successfully";
        return "no Result Gotten From request!";
    }


    @PostMapping(path = "/discount", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String insertDiscountRedemption(@RequestBody @Validated final String code) {
        if (redeemVoucher.redeemDiscountVoucher(code)) return "Discount Voucher redeemed Successfully";
        return "no Result Gotten From request!";
    }


    @PostMapping(path = "/value", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String insertValueRedemption(@RequestBody @Validated final String code) {
        if (redeemVoucher.redeemValueVoucher(code)) return "Value Voucher redeemed Successfully";
        return "no Result Gotten From request!";
    }

}
