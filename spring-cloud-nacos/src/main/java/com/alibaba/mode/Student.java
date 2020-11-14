package com.alibaba.mode;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Student {

    @JSONField(name = "student_name")
    public String name;
    @JSONField(name = "student_age")
    public Integer age;
    @JSONField(name = "student_address")
    public String address;
}
