package com.interswitch.redemptionapi;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import lombok.var;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RedemptionapiApplication {

    static final String GIFT_EXCHANGE_NAME = "gift-exchange";
    static final String GIFT_ONE = "gift-one";
    static final String GIFT_TWO = "gift-two";
    static final String GIFT_THREE = "gift-three";


    public static void main(String[] args) {
        SpringApplication.run(RedemptionapiApplication.class, args);
    }

    @Bean
    public TopicExchange giftExchange() {
        return new TopicExchange(GIFT_EXCHANGE_NAME);
    }

    @Bean
    public Queue giftQueueOne() {
        return new Queue(GIFT_ONE);
    }

    @Bean
    public Queue giftQueueTwo() {
        return new Queue(GIFT_TWO);
    }

    @Bean
    public Queue giftQueueThree() {
        return new Queue(GIFT_THREE);
    }

    @Bean
    public Binding BindingGiftOne() {
        return BindingBuilder.bind(giftQueueOne()).to(giftExchange()).with("gift-one");
    }

    @Bean
    public Binding BindingGiftTwo() {
        return BindingBuilder.bind(giftQueueTwo()).to(giftExchange()).with("gift-two");
    }

    @Bean
    public Binding BindingGiftThree() {
        return BindingBuilder.bind(giftQueueThree()).to(giftExchange()).with("gift-three");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder builder) {
                builder.dateFormat(new ISO8601DateFormat());
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

