package com.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.service.AdminFeign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jidn
 * @Date 2020/10/28
 */
@RestController
public class ConsumerController {

    @Resource
    private AdminFeign adminFeign ;

    @RequestMapping(value = "/getMsg",method = RequestMethod.GET)
    public String consumer(){
        // 根据服务名从注册中心获取一个健康的服务实例
        return adminFeign.getMsg("tom");
    }
}
