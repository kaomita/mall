package com.macro.mall.portal.component;

import com.macro.mall.portal.domain.CouponMessage;
import com.macro.mall.portal.domain.QueueEnum;
import com.macro.mall.portal.util.RabbitMessageSenderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 抢购优惠卷成功后将优惠卷信息加入到消息队列
 * */
@Component
public class CouponSaleSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponSaleSender.class);
    private final RabbitTemplate rabbitTemplate;
    public CouponSaleSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCouponMessage(CouponMessage msg) {
        RabbitMessageSenderUtil.sendWithConfirm(rabbitTemplate, QueueEnum.QUEUE_COUPON_SALE.getExchange(), QueueEnum.QUEUE_COUPON_SALE.getRouteKey(),
                msg);
        LOGGER.info("send couponId: {}, user: {}, data: {}", msg.getCouponId(), msg.getNickname(), msg.getTime());
    }
}
