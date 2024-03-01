package com.yp.nowcode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yp.nowcode.constant.BiMqConstant;
import com.yp.nowcode.utils.SpringContextUtils;
import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主类（项目启动入口）
 */
@SpringBootApplication
@MapperScan("com.yp.nowcode.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDubbo
@Slf4j
public class MainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        String[] allBeansNames = SpringContextUtils.getAllBeansNames();
    }

    @PostConstruct
    public void initBiMq() {
        log.info("初始化消息队列和交换机");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            String EXCHANGE_NAME = BiMqConstant.BI_EXCHANGE_NAME;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY);

//            channel.queueDeclare(BiMqConstant.BI_RETRY_QUEUE_NAME, true, false, false, null);
//            channel.queueBind(BiMqConstant.BI_RETRY_QUEUE_NAME, EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY);
        } catch (TimeoutException | IOException e) {
            log.error("创建消息队列异常", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建消息队列异常");
        }
    }

}
