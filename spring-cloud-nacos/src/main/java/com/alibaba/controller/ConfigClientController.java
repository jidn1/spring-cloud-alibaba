package com.alibaba.controller;

import com.alibaba.config.NacosConfigInfo;
import com.alibaba.config.NacosConfigLocalCatch;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.mode.Student;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Properties;

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

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "127.0.0.1:8848");

        //2、创建ConfigService对象
        try {
            ConfigService configService = NacosFactory.createConfigService(properties);
            String default_group = configService.getConfig("spring-cloud-alibaba-nacos-dev.yaml", "DEFAULT_GROUP", 5000L);
            List<Student> students = JSONObject.parseArray(default_group, Student.class);
            System.out.println(JSONObject.toJSONString(students));
        } catch (NacosException e) {
            e.printStackTrace();
        }

//        List blackUserIdList = nacosConfigLocalCatch
//                .getLocalCatchConfig(NacosConfigInfo.BLACK_USERID_LIST, List.class);
//        System.out.println(blackUserIdList);
        return "ok";
    }
}
