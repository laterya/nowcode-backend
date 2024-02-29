package com.yp.nowcode.job.once;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yp.nowcode.constant.BiMqConstant;
import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yp
 * @date: 2024/1/26
 * 创建消息队列和交换机
 * 单次任务的顺序是在程序运行后，但是程序运行时加载RabbitMQ，需要有这消息队列和交换机
 */
//@Component
@Slf4j
@Deprecated
public class InitBiMq implements CommandLineRunner {
    @Override
    public void run(String... args) {
        log.info("初始化消息队列和交换机");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            String EXCHANGE_NAME = BiMqConstant.BI_EXCHANGE_NAME;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct",
                    // 添加可选参数，设置为持久化以应对服务器重启等情况
                    true);

            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY);

        } catch (TimeoutException | IOException e) {
            log.error("创建消息队列异常", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建消息队列异常");
        }
    }
}