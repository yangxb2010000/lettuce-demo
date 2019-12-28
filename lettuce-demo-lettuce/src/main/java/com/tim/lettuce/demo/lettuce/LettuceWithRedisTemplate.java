package com.tim.lettuce.demo.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xiaobing
 * @date 2019/12/20
 */
@Component
@SpringBootApplication
public class LettuceWithRedisTemplate {
    /**
     * SpringBoot autoconfigure在classpath中发现Lettuce class会自动注入StringRedisTemplate
     */
    @Autowired
    StringRedisTemplate redisTemplate;

    public void testRedisTemplate() {
        //redisTemplate封装了对Lettuce StatefulRedisConnection的调用
        redisTemplate.opsForValue().set("key", "val123");

        String value = redisTemplate.opsForValue().get("key");
        System.out.println("get redis value with RedisTemplate, value is :" + value);

        redisTemplate.delete("key");
    }

    public static void main(String[] args) {
        // 创建SpringApplicationContext容器
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LettuceWithRedisTemplate.class, args);
        //从容器中获取测试Bean
        LettuceWithRedisTemplate lettuceWithRedisTemplate = applicationContext.getBean(LettuceWithRedisTemplate.class);
        lettuceWithRedisTemplate.testRedisTemplate();
    }
}
