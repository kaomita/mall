package com.macro.mall.portal.component;

import com.macro.mall.portal.domain.CouponMessage;
import com.macro.mall.portal.service.UmsMemberCouponCacheService;
import com.macro.mall.portal.service.UmsMemberCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class CouponSaleReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponSaleReceiver.class);

    private final UmsMemberCouponService memberCouponService;
    private final UmsMemberCouponCacheService memberCouponCacheService;

    public CouponSaleReceiver(UmsMemberCouponService memberCouponService, UmsMemberCouponCacheService memberCouponCacheService) {
        this.memberCouponService = memberCouponService;
        this.memberCouponCacheService = memberCouponCacheService;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "mall.sale.coupon"),
            exchange = @Exchange(name = "mall.sale.direct", type = ExchangeTypes.DIRECT),
            key = "mall.sale.coupon"
    ))
    public void handle(CouponMessage msg, @Header("msgId") String msgId) {
        // 使用setnx msgId 解决幂等问题
        if(!memberCouponCacheService.setIfAbsence(msgId)) {
            // 该消息已经被处理
            LOGGER.info("message handled already, message ID: {}", msgId);
            return;
        }
        memberCouponService.processCoupon(msg);
        LOGGER.info("handled message msgId: {},  couponId: {}, user: {}, data: {}",msgId, msg.getCouponId(), msg.getNickname(), msg.getTime());
    }
}
