package com.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jidn
 * @Date 2020/10/28
 */
@FeignClient("spring-cloud-alibaba-admin")
public interface AdminFeign {

    @GetMapping("/produce")
    String getMsg (@RequestParam(name = "name") String name);
}
