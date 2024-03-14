package com.yp.nowcode.mq;

import com.github.rholder.retry.*;
import com.rabbitmq.client.Channel;
import com.yp.nowcode.constant.BiMqConstant;
import com.yp.nowcode.constant.ChartStatusConstant;
import com.yp.nowcode.manager.AiManager;
import com.yp.nowcode.model.entity.Chart;
import com.yp.nowcode.service.ChartService;
import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BiMessageConsumer {

    @Resource
    private ChartService chartService;

    @Resource
    private AiManager aiManager;


    // 指定程序监听的消息队列和确认机制
    @RabbitListener(queues = {BiMqConstant.BI_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("receiveMessage message = {}", message);
        if (StringUtils.isBlank(message)) {
            // 如果失败，消息拒绝
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if (chart == null) {
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "图表为空");
        }
        // 先修改图表任务状态为 “执行中”。等执行成功后，修改为 “已完成”、保存执行结果；执行失败后，状态修改为 “失败”，记录任务失败信息。
        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        // 如果状态为运行中防止重复消费，成功失败的状态可以重新生成
        if (chart.getStatus().equals(ChartStatusConstant.Gen_RUNNING)) {
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在生成中");
        }
        updateChart.setStatus(ChartStatusConstant.Gen_RUNNING);
        boolean b = chartService.updateById(updateChart);
        if (!b) {
            channel.basicNack(deliveryTag, false, true);
            handleChartUpdateError(chart.getId(), "更新图表执行中状态失败");
            return;
        }
        channel.basicAck(deliveryTag, false);

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .retryIfResult(res -> !res)  //设置根据结果重试
                .withWaitStrategy(WaitStrategies.fixedWait(3, TimeUnit.SECONDS)) //设置等待间隔时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) //设置最大重试次数
                .build();

        try {
            retryer.call(() -> extracted(chart));
        } catch (ExecutionException | RetryException e) {
            handleChartUpdateError(chart.getId(), "AI 生成失败");
            log.info("retryer error", e);
        }
    }


    private boolean extracted(Chart chart) {
        try {
            String result = aiManager.doChatUseXf(chart.getGoal(), chart.getChartData());
            String[] strings = chartService.handleAiRet(result);
            String genChart = strings[0];
            String genResult = strings[1];
            Chart updateChartResult = new Chart();
            updateChartResult.setId(chart.getId());
            updateChartResult.setGenChart(genChart);
            updateChartResult.setGenResult(genResult);
            updateChartResult.setStatus(ChartStatusConstant.GEN_SUCCEED);
            return chartService.updateById(updateChartResult);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 生成错误");
        }
    }

    private void handleChartUpdateError(long chartId, String execMessage) {
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus(ChartStatusConstant.GEN_REQUEST_FAILED);
        updateChartResult.setExecMessage("execMessage");
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult) {
            log.error("更新图表失败状态失败" + chartId + "," + execMessage);
        }
    }

}

