package com.macro.mall.portal.component;

import com.macro.mall.portal.domain.CouponMessage;
import com.macro.mall.portal.service.UmsMemberCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class CouponSaleReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponSaleReceiver.class);

    private final UmsMemberCouponService memberCouponService;

    public CouponSaleReceiver(UmsMemberCouponService memberCouponService) {
        this.memberCouponService = memberCouponService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "mall.sale.coupon"),
            exchange = @Exchange(name = "mall.sale.direct", type = ExchangeTypes.DIRECT),
            key = "mall.sale.coupon"
    ))
    public void handle(CouponMessage msg) {
        memberCouponService.processCoupon(msg);
        LOGGER.info("handled message couponId: {}, user: {}, data: {}", msg.getCouponId(), msg.getNickname(), msg.getTime());
    }
}
