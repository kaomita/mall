package com.macro.mall.portal.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component

public class ErrorRabbitMQ {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "mall.error"),
            exchange = @Exchange(name = "mall.error.direct"),
            key = "mall.sale.error"
    ))
    void handle() {
        log.info("消费者发生了错误");
    }
}
