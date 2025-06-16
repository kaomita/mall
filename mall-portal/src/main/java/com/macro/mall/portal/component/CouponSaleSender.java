package com.macro.mall.portal.component;

import com.macro.mall.portal.domain.CouponMessage;
import com.macro.mall.portal.domain.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 抢购优惠卷成功后将优惠卷信息加入到消息队列
 * */
@Component
public class CouponSaleSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponSaleSender.class);
    private final AmqpTemplate amqpTemplate;
    public CouponSaleSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendCouponMessage(CouponMessage msg) {
        // 将多个数据封装进实体类，编译序列化和反序列化
        amqpTemplate.convertAndSend(
                QueueEnum.QUEUE_COUPON_SALE.getExchange(), QueueEnum.QUEUE_COUPON_SALE.getRouteKey(),
                msg);
        LOGGER.info("send couponId: {}, user: {}, data: {}", msg.getCouponId(), msg.getNickname(), msg.getTime());
    }
}
