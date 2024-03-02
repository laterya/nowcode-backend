package com.yp.nowcode.manager;

import com.yp.aigcsdk.service.AiService;
import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 导入的ai管理器
 */
@Service
public class AiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;

    @Resource
    private AiService aiService;

    private static final String PRECONDITION = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "{csv格式的原始数据，用,作为分隔符}\n" +
            "请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "【【【【【\n" +
            "{前端Echarts V5 的 option 配置对象 JSON代码，不要生成任何多余的内容，比如注释和代码块标记}\n" +
            "【【【【【\n" +
            "{明确的数据分析结论，越详细越好，不要生成多余的注释}\n" +
            "最终格式是：【【【【【前端代码【【【【【分析结论\n";

    /**
     * AI 对话
     *
     * @param modelId
     * @param message
     * @return
     */
    public String doChat(long modelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if (response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 响应错误");
        }
        return response.getData().getContent();
    }

    public String doChatUseXf(String goal, String csvData) {
        StringBuffer sb = new StringBuffer();
        sb.append("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n分析需求：\n").append(goal).append("\n").append("原始数据：\n")
                .append(csvData).append("请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n【【【【【\n")
                .append("{前端Echarts V5 的 option 配置对象 JSON代码，不要生成任何多余的内容，比如注释和代码块标记}\n").append("【【【【【\n")
                .append("{明确的数据分析结论，越详细越好，不要生成多余的注释}\n").append("最终格式是：【【【【【前端代码【【【【【分析结论\n");
        String tmp = sb.toString();
        String res = aiService.doChat(sb.toString());
        return res;
    }
}
