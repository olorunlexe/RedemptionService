package com.interswitch.redemptionapi;

import com.interswitch.redemptionapi.Domain.DiscountResult;
import com.interswitch.redemptionapi.Domain.GiftResult;
import com.interswitch.redemptionapi.Domain.ValueResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


//@Profile("rabbitmq-template")
@Component//("templateOrderReceiver")
public class VoucherReceiver {

    private static final Logger log = LoggerFactory.getLogger(VoucherReceiver.class);

    private RabbitTemplate rabbitTemplate;

    public VoucherReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public GiftResult receiveGiftVoucher() {
        log.info("receive Gift Voucher Details from  VoucherApi");
        return (GiftResult) rabbitTemplate.receiveAndConvert("q1");
    }

    public DiscountResult receiveDiscountVoucher() {
        log.info("receive Gift Voucher Details from  VoucherApi");
        return (DiscountResult) rabbitTemplate.receiveAndConvert("q2");
    }

    public ValueResult receiveValueVoucher() {
        log.info("receive Gift Voucher Details from  VoucherApi");
        return (ValueResult) rabbitTemplate.receiveAndConvert("q3");
    }

}
