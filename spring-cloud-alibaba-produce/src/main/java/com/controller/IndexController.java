package com.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jidn
 * @Date 2020/10/28
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String hello() {
        return "hello spring cloud alibaba";
    }


    @GetMapping("/produce")
    public String getProduce(@RequestParam("name") String name) {
        return "Wow Surprise welcome to cloud "+name;
    }
}
