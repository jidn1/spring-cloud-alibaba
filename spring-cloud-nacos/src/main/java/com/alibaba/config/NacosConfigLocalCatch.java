package com.alibaba.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class NacosConfigLocalCatch implements InitializingBean {

    // 注入 Nacos Config server 相关 springboot 配置
    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    // 根据 配置量 大小 ，初始化容量
    // key：dataId
    // value：具体配置数据实体对象
    private Map<String, Object> localCatchMap = new HashMap<>(16);

    private ConfigService configService;

    // 根据枚举 及 类型 获取 配置实体
    public <T> T getLocalCatchConfig(NacosConfigInfo configInfo, Class<? extends T> cls) {
        Object data = this.localCatchMap.get(configInfo.getDataId());
        // 判断data这个对象能不能被转化为cls这个类
        boolean instance = cls.isInstance(data);
        if(instance) {
            return (T) data;
        }
        throw new IllegalArgumentException("类型转换错误");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 获取 所有 跟 Nacos 具体配置 映射的枚举实例
        NacosConfigInfo[] nacosConfigInfos = NacosConfigInfo.values();

        // 获取 Nacos 服务端 具体配置
        String serverAddr = nacosConfigProperties.getServerAddr();
        String namespace = nacosConfigProperties.getNamespace();

        // 封装 Nacos server 配置参数
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        if (configService == null) {
            configService = NacosFactory.createConfigService(properties);
        }

        // 遍历 获取 配置 ，并且针对各配置 设置 动态监听对象
        for (NacosConfigInfo configInfo : nacosConfigInfos) {
            String dataId = configInfo.getDataId();
            String group = configInfo.getGroup();
            // 初始化 获取具体配置
            String config = configService.getConfig(dataId, group, 1000);
            // 转换json保存到localCatchMap
            this.jsonToObject(config, configInfo);
            // 创建监听对象
            Listener listener = new NacosJsonConfigDataListener(configInfo);
            // 配置 具体配置项的监听
            configService.addListener(configInfo.getDataId(), configInfo.getGroup(), listener);
        }
    }

    // 将获取到的json配置装换为具体对象存储到localCatchMap
    public void jsonToObject(String json, NacosConfigInfo configInfo) {
        Object data = JSON.parseObject(json, configInfo.getCls());

        this.localCatchMap.put(configInfo.getDataId(), data);
    }

    // 配置用于异步监听的线程池
    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2,4,1,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<>(100),
                    new ThreadPoolExecutor.CallerRunsPolicy());

    // 具体监听类 动态监听Nacos配置
    public class NacosJsonConfigDataListener implements Listener {
        private NacosConfigInfo configInfo;
        private NacosJsonConfigDataListener(NacosConfigInfo configInfo) {
            this.configInfo = configInfo;
        }

        // 配置线程执行器 用于异步监听
        @Override
        public Executor getExecutor() {
            return executor;
        }

        // 动态监听到变动后的处理逻辑
        @Override
        public void receiveConfigInfo(String jsonStr) {
            jsonToObject(jsonStr, configInfo);
        }
    };
}
