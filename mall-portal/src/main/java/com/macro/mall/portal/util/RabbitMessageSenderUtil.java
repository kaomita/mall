package com.macro.mall.portal.util;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class RabbitMessageSenderUtil {
    /**
     * 发送消息并绑定确认回调
     *
     * @param rabbitTemplate 消息模板
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息体（必须可序列化）
     */
    public static void sendWithConfirm(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object message) {
        String msgId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(msgId);

        correlationData.getFuture().whenComplete((confirm, ex) -> {
            if (ex != null) {
                log.error("RabbitMQ消息发送失败：{}", ex.getMessage(), ex);
                // TODO: 可记录数据库用于补偿
            } else {
                if (confirm.isAck()) {
                    log.info("消息成功发送并确认，msgId = {}", correlationData.getId());
                } else {
                    log.error("消息被拒绝，msgId = {}, 原因 = {}", correlationData.getId(), confirm.getReason());
                    // TODO: 可记录数据库用于补偿
                }
            }
        });

        // 将uuid一起发送给消费者
        rabbitTemplate.convertAndSend(exchange, routingKey, message, msg -> {
            msg.getMessageProperties().setHeader("msgId", msgId);
            return msg;
        }, correlationData);
    }
}
