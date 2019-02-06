package com.interswitch.redemptionapi.Controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interswitch.redemptionapi.Domain.DiscountResult;
import com.interswitch.redemptionapi.Domain.GiftResult;
import com.interswitch.redemptionapi.Domain.Redemption;
import com.interswitch.redemptionapi.Domain.ValueResult;
import com.interswitch.redemptionapi.Service.GiftRedemptionService;
import com.interswitch.redemptionapi.Service.IRedemptionService;
import com.interswitch.redemptionapi.VoucherReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Controller
@RequestMapping("/api/v1")
public class RedemptionController {


    private static final Logger log = LoggerFactory.getLogger(RedemptionController.class);
    private IRedemptionService redemptionService;
    private RabbitTemplate rabbitTemplate;
    private VoucherReceiver voucherReceiver;
    private Redemption redemption;
    private GiftRedemptionService giftRedemptionService;


    @Autowired
    public RedemptionController(IRedemptionService redemptionService, RabbitTemplate rabbitTemplate,
                                VoucherReceiver voucherReceiver,  GiftRedemptionService giftRedemptionService) {
        this.redemptionService = redemptionService;
        this.rabbitTemplate = rabbitTemplate;
        this.voucherReceiver = voucherReceiver;
        this.giftRedemptionService = giftRedemptionService;
    }

    @PostMapping(path = "/gift", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String insertGiftRedemption(@RequestBody @Validated final Redemption redemption) throws IOException {
        log.info("Sending message...");
        System.out.println("Sending message...");
        final String uri = "http://localhost:63745/api/v1/gift/" + redemption.getCode();
        RestTemplate restTemplate = new RestTemplate();
        GiftResult receiveGift = restTemplate.getForObject(uri, GiftResult.class);
        ResponseEntity<String> response
                = restTemplate.getForEntity(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        if (receiveGift != null && receiveGift.getGiftBalance()>=redemption.getGiftAmountRedeemed()) {
            giftRedemptionService.redeemGiftVoucher(redemption.getGiftAmountRedeemed(), receiveGift, redemption, rabbitTemplate, redemptionService);
            return "Gift Voucher redeemed Successfully";
        }
        return "no Result Gotten From request!";
    }


    @PostMapping(path = "/discount", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String insertDiscountRedemption(@RequestBody @Validated final String code) {
        log.info("Sending message...");
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend("spring-amqp-exchange", "q2", code);
        DiscountResult receiveDiscount = voucherReceiver.receiveDiscountVoucher();
        if (receiveDiscount != null) {

            if (receiveDiscount.getDiscountAmount() > 0) {
                redemption.setDiscountAmountRedeemed(receiveDiscount.getDiscountAmount());
            } else if (receiveDiscount.getDiscountPercent() > 0) {
                redemption.setDiscountPercentRedeemed(receiveDiscount.getDiscountPercent());
            } else if (receiveDiscount.getDiscountUnit() > 0) {
                redemption.setDiscountUnitRedeemed(receiveDiscount.getDiscountUnit());
            }
            redemption.setVoucherType(receiveDiscount.getVoucherType());
            redemption.setMerchantId(receiveDiscount.getMerchantId());
            log.info("Creating redemption" + redemption.toString());
            redemptionService.createRedemption(redemption);

            return "Discount Voucher redeemed Successfully";
        }
        return "no Result Gotten From request!";
    }


    @PostMapping(path = "/value", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String insertValueRedemption(@RequestBody @Validated final String code) {
        Redemption checkRedemptionTableForCode = redemptionService.readRedemptionByCode(code);
        if (checkRedemptionTableForCode != null) {
            return "Value Voucher has Been Redeemed!";
        } else {
            log.info("Sending message...");
            System.out.println("Sending message...");
            rabbitTemplate.convertAndSend("spring-amqp-exchange", "q3", code);
            ValueResult receiveValue = voucherReceiver.receiveValueVoucher();
            if (receiveValue != null) {
                redemption.setValueAmountRedeemed(receiveValue.getValueAmount());
                redemption.setVoucherType(receiveValue.getVoucherType());
                redemption.setMerchantId(receiveValue.getMerchantId());
                log.info("Creating redemption" + redemption.toString());
                redemptionService.createRedemption(redemption);

                return "Value Voucher redeemed Successfully";
            }
        }
        return "no Result Gotten From request!";
    }

}
