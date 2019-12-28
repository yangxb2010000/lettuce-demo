package com.tim.lettuce.demo.jedis;

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
public class JedisWithRedisTemplate {
    /**
     * SpringBoot autoconfigure在classpath中发现Jedis class会自动注入StringRedisTemplate
     */
    @Autowired
    StringRedisTemplate redisTemplate;

    public void testRedisTemplate() {
        //redisTemplate封装了对Jedis的获取和释放、并使用JedisPool连接池
        redisTemplate.opsForValue().set("key", "val123");

        String value = redisTemplate.opsForValue().get("key");
        System.out.println("get redis value with RedisTemplate, value is :" + value);

        redisTemplate.delete("key");
    }

    public static void main(String[] args) {
        // 创建SpringApplicationContext容器
        ConfigurableApplicationContext applicationContext = SpringApplication.run(JedisWithRedisTemplate.class, args);
        //从容器中获取测试Bean
        JedisWithRedisTemplate jedisWithRedisTemplate = applicationContext.getBean(JedisWithRedisTemplate.class);
        jedisWithRedisTemplate.testRedisTemplate();
    }
}
