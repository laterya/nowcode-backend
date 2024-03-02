package com.yp.nowcode.manager;

import com.yp.aigcsdk.service.AiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author yp
 * @date: 2024/3/2
 */
@SpringBootTest
public class AiManagerTest {

    @Resource
    private AiService aiService;

    @Test
    void testXfxh() {
        String string = aiService.doChat("给我讲个笑话");
        System.out.println(string);
    }
}
