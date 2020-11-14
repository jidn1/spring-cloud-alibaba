package com.alibaba.controller;

import com.alibaba.config.NacosConfigInfo;
import com.alibaba.config.NacosConfigLocalCatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jidn
 * @Date 2020/10/28
 */


@RestController
@RefreshScope
public class ConfigClientController {


//    @Value("${profile}")
//    private String profile;

    @Autowired
    private NacosConfigLocalCatch nacosConfigLocalCatch;

//    @GetMapping("/profile")
//    public String hello() {
//        return this.profile;
//    }

    @RequestMapping("/test2")
    public String test2() {
        List blackUserIdList = nacosConfigLocalCatch
                .getLocalCatchConfig(NacosConfigInfo.BLACK_USERID_LIST, List.class);
        System.out.println(blackUserIdList);
        return "ok";
    }
}
