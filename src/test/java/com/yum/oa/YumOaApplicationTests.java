package com.yum.oa;

import com.yum.oa.common.security.JwtUser;
import com.yum.oa.common.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
class YumOaApplicationTests {
    @Resource
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        JwtUser user = new JwtUser(1, "张三", "123456", new ArrayList<>());

        System.out.println(jwtUtil.generateAccessToken(user));
    }

}
