package com.alibaba.config;

import java.util.List;

public enum NacosConfigInfo {


    BLACK_USERID_LIST("spring-cloud-alibaba-nacos-dev.yaml",
                              "DEFAULT_GROUP", List.class, "黑名单");

    private String dataId;
    private String group;
    private Class cls;
    private String desc;

    private NacosConfigInfo(String dataId, String group, Class cls, String desc) {
        this.dataId = dataId;
        this.group = group;
        this.cls = cls;
        this.desc = desc;
    }

    public String getDataId() {
        return dataId;
    }

    public String getGroup() {
        return group;
    }

    public Class getCls() {
        return cls;
    }

    public String getDesc() {
        return desc;
    }
}
