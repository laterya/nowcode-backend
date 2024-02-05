package com.yp.nowcode.mq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author yp
 * @date: 2024/1/27
 */
@SpringBootTest
@Slf4j
public class Comsumer {

    @SneakyThrows
    @Test
    @RabbitListener(queues = {"bi_queue_test"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage:{}", message);
        Thread.sleep(10000);
        try {
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("ack error", e);
        }
    }
}
