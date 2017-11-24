package com.company;

import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class RedisTest {

    /**
     * 连接到 Redis服务
     */
    @Test
    public void test001() {
        //连接本地的 Redis服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
    }

    /**
     * Redis Java String(字符串) 实例
     */
    @Test
    public void test002() {
        //连接本地的 Redis服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");

        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get("runoobkey"));
    }

    /**
     * Redis Java List(列表) 实例
     */
    @Test
    public void test003() {
        //连接本地的 Redis服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0, 2);
        for (String aList : list) {
            System.out.println("列表项为: " + aList);
        }
    }

    /**
     * Redis Java Keys 实例
     */
    @Test
    public void test004() {
        //连接本地的 Redis服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");

        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

}
