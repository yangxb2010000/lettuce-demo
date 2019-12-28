package com.tim.lettuce.demo.lettuce;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisStringCommands;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * @author xiaobing
 * @date 2019/12/20
 */
public class LettuceSimpleUse {
    /**
     * 创建一个线程安全的StatefulRedisConnection，可以多线程并发对该connection操作,底层只有一个物理连接.
     * @return
     */
    private StatefulRedisConnection<String, String> getRedisConnection() {
        //构建RedisClient对象，RedisClient包含了Redis的基本配置信息，可以基于RedisClient创建RedisConnection
        RedisClient client = RedisClient.create("redis://localhost");

        client.setOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofSeconds(5)).build()).build());

        return client.connect();
    }

    private void testLettuce() throws ExecutionException, InterruptedException {
        //构建RedisClient对象，RedisClient包含了Redis的基本配置信息，可以基于RedisClient创建RedisConnection
        RedisClient client = RedisClient.create("redis://localhost");

        client.setOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofSeconds(5)).build()).build());

        //创建一个线程安全的StatefulRedisConnection，可以多线程并发对该connection操作,底层只有一个物理连接.
        StatefulRedisConnection<String, String> connection = getRedisConnection();

        //获取SyncCommand。Lettuce支持SyncCommand、AsyncCommands、ActiveCommand三种command
        RedisStringCommands<String, String> sync = connection.sync();
        String value = sync.get("key");
        System.out.println("get redis value with lettuce sync command, value is :" + value);

        //获取SyncCommand。Lettuce支持SyncCommand、AsyncCommands、ActiveCommand三种command
        RedisAsyncCommands<String, String> async = connection.async();
        RedisFuture<String> getFuture = async.get("key");
        value = getFuture.get();
        System.out.println("get redis value with lettuce async command, value is :" + value);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new LettuceSimpleUse().testLettuce();
    }
}
