package com.tim.lettuce.demo.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

/**
 * @author xiaobing
 * @date 2019/12/20
 */
public class JedisSimpleUse {
    private String host = "localhost";
    private int port = 6379;
    private String password = "password";

    /**
     * 直接构建Jedis实例的方式使用Jedis
     */
    public void useJedis() {
        //指定Redis服务Host和port， Jedis是非线程安全的，只能单个线程访问，每个线程都要单独构建Jedis对象
        Jedis jedis = new Jedis(host, port);
        try {
            //如果Redis服务连接需要密码，指定密码
            jedis.auth(password);
            //访问Redis服务
            String value = jedis.get("key");
            System.out.println("get redis value with Jedis, value is :" + value);
        } finally {
            //使用完关闭连接
            jedis.close();
        }
    }

    private JedisPool jedisPool;

    /**
     * 初始化JedisPool
     */
    public void initJedisPool() {
        GenericObjectPoolConfig genericObjectPool = new GenericObjectPoolConfig();
        jedisPool = new JedisPool(genericObjectPool, host, port, Protocol.DEFAULT_TIMEOUT, password);
    }

    /**
     * 基于连接池的方式使用Jedis
     */
    public void useJedisPool() {
        Jedis jedis = jedisPool.getResource();
        try {
            //访问Redis服务
            String value = jedis.get("key");
            System.out.println("get redis value with JedisPool, value is :" + value);
        } finally {
            //使用完关闭连接
            jedis.close();
        }
    }

    public static void main(String[] args) {
        JedisSimpleUse jedisSimpleUse = new JedisSimpleUse();
        //调用Jedis实例方法
        jedisSimpleUse.useJedis();

        // 初始化JedisPool，只需要初始化一次
        jedisSimpleUse.initJedisPool();

        // 多次基于JedisPool调用Redis
        jedisSimpleUse.useJedisPool();
        jedisSimpleUse.useJedisPool();
    }
}
