package cn.qf.redis;

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月19日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-19 18:00
 * @ desc:  
 *
 * @author taoxuefeng*/

public class JavaRedisTest {

    @Test
    public void jedisTest() {
        Jedis jedis = new Jedis("192.168.8.101", 6379);
        //jedis.set("s4","jedis test");
        List<String> userinfo = jedis.hmget("userinfo", "username", "age", "height", "weight");
        System.out.println(userinfo);
        jedis.close();
    }
}
