package com.yum.oa.controller;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-05
 * @version: v2.0
 **/
@RestController
@RequestMapping("/api")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @PostMapping("login")
    public ResultBean<?> login() {
        logger.info("请求成功");
        return ResultGenerator.success("登录成功");
    }

    @GetMapping("getInfo")
    public ResultBean<?> getUserInfo() {
        return ResultGenerator.success("李四");
    }
}
