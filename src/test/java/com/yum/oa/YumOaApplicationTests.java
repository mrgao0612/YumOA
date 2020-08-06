package com.yum.oa;

import com.yum.oa.common.redis.RedisKeyGenerator;
import com.yum.oa.common.redis.RedisUtil;
import com.yum.oa.common.security.Authority;
import com.yum.oa.common.security.JwtUser;
import com.yum.oa.common.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;

@SpringBootTest
class YumOaApplicationTests {
    @Value("${jwt.tokenStart}")
    private String start;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        Authority auth = new Authority();
        auth.setAuth("ALL");
        JwtUser user = new JwtUser(1, "张三", "123456", Collections.singleton(auth));

        String token = start + jwtUtil.generateAccessToken(user);

        redisUtil.set(RedisKeyGenerator.getUserToken(1L), token);
    }

}
