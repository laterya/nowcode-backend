package com.yp.nowcode.service;

import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import org.junit.jupiter.api.Test;

/**
 * @author yp
 * @date: 2024/3/3
 */
class ChartServiceTest {

    @Test
    void handleAiRet() {
        String aiRet = "【【【【【前端代码\n" +
                "{\n" +
                "  \"title\": {\n" +
                "    \"text\": \"用户增长情况分析\"\n" +
                "  },\n" +
                "  \"tooltip\": {},\n" +
                "  \"legend\": {\n" +
                "    \"data\": [\"用户数\"]\n" +
                "  },\n" +
                "  \"xAxis\": {\n" +
                "    \"data\": [\"1号\", \"2号\", \"3号\", \"4号\", \"5号\", \"6号\", \"7号\", \"8号\", \"9号\", \"10号\", \"11号\", \"12号\"]\n" +
                "  },\n" +
                "  \"yAxis\": {},\n" +
                "  \"series\": [\n" +
                "    {\n" +
                "      \"name\": \"用户数\",\n" +
                "      \"type\": \"line\",\n" +
                "      \"data\": [26, 53, 84, 23, 57, 48, 62, 89, 14, 35, 78, 34]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "【【【【【分析结论\n" +
                "根据给定的数据，我们可以得出以下结论：\n" +
                "\n" +
                "1. 用户数在8号达到最高点，为89人。\n" +
                "2. 用户数在9号出现明显下降，仅为14人。\n" +
                "3. 整体来看，用户数呈现波动性增长，但在9号出现异常波动。\n" +
                "4. 从1号到8号，用户数整体呈现上升趋势，但增长速度不稳定。\n" +
                "5. 从10号到12号，用户数呈现下降趋势。";

        String[] splits = aiRet.split("【【【【【");
        if (splits.length < 3) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 生成错误");
        }
        String genChart = splits[1].trim().substring(splits[1].indexOf('{'));
        String genResult = splits[2].trim().substring(5);
        System.out.println(genChart);
        System.out.println(genResult);
    }
}