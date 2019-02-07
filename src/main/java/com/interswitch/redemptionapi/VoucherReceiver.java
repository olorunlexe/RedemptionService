package com.interswitch.redemptionapi;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interswitch.redemptionapi.Domain.DiscountResult;
import com.interswitch.redemptionapi.Domain.GiftResult;
import com.interswitch.redemptionapi.Domain.ValueResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;


//@Profile("rabbitmq-template")
@Component//("templateOrderReceiver")
public class VoucherReceiver {

    private static final Logger log = LoggerFactory.getLogger(VoucherReceiver.class);

    private RabbitTemplate rabbitTemplate;
    private MessageConverter messageConverter;

    public VoucherReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = rabbitTemplate.getMessageConverter();
    }

    public GiftResult receiveGiftVoucher() {
        log.info("receive Gift Voucher Details from  VoucherApi");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        GiftResult giftResult = mapper.convertValue(rabbitTemplate.receiveAndConvert("gift-two"), GiftResult.class);
        return giftResult;
    }

    public DiscountResult receiveDiscountVoucher() {
        log.info("receive Discount Voucher Details from  VoucherApi");
        return (DiscountResult) rabbitTemplate.receiveAndConvert("");
    }

    public ValueResult receiveValueVoucher() {
        log.info("receive Value Voucher Details from  VoucherApi");
        return (ValueResult) rabbitTemplate.receiveAndConvert("q3");
    }

}
