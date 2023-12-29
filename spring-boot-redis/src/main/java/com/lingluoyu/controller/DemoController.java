package com.lingluoyu.controller;

import com.lingluoyu.controller.model.DemoModel;
import com.lingluoyu.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("get")
    public DemoModel getDemoModel() {
        DemoModel model = new DemoModel();
        model.setId(1);
        model.setName("demo");

        String key = "demo" + model.getId();

        redisUtil.setIfAbsent(key, model);

        return (DemoModel) redisUtil.getObject(key);
    }
}
