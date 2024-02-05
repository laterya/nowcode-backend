package com.yp.nowcodeinterfaces.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yp.nowcodeinterfaces.utils.RequestUtils.get;

/**
 * @author yp
 * @date: 2024/2/1
 */
@RestController
@RequestMapping("/")
public class InterfacesController {
    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        return get("https://api.vvhan.com/api/love");
    }

    @GetMapping("/poisonousChickenSoup")
    public String getPoisonousChickenSoup() {
        return get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }
}
