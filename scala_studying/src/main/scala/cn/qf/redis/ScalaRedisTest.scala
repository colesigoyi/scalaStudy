package cn.qf.redis

import redis.clients.jedis.Jedis

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月19日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ScalaRedisTest {
  def main(args: Array[String]): Unit = {
    val jedis: Jedis = new Jedis("192.168.8.101", 6379)
    val keyname: String = "userInfo2"
    jedis.hset(keyname,"username", "jack")
    jedis.hset(keyname,"age", "27")
    jedis.hset(keyname,"height", "178")
    jedis.hset(keyname,"weight", "49")

    println(s"用户姓名:${jedis.hget(keyname,"username")}," +
      s"年龄:${jedis.hget(keyname,"age")}," +
      s"体重:${jedis.hget(keyname,"weight")}," +
      s"身高:${jedis.hget(keyname,"height")}")
    /*
    用户姓名:jack,年龄:27,体重:49,身高:178
     */
    jedis.close()
  }
}
