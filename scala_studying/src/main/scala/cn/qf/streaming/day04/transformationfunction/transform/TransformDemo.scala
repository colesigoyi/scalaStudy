package cn.qf.streaming.day04.transformationfunction.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

/**
 * 需求:通过恶意黑名单用户,得到有效的用户信息
 * 思路:
 * 1.获取源数据和黑名单数据
 * 2.将源数据和黑名单用户进行join
 * 3.用黑名单进行过滤
 * 4.得到最终的有效用户数据
 */
object TransformDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(this.getClass.getSimpleName)
    val sc = new StreamingContext(conf, Seconds(5))
    
    //获取数据,样例数据:"zhangsan zhaoliu wangwu huahua lili lisi"
    val lines: ReceiverInputDStream[String] =
      sc.socketTextStream("NODE01", 6666)
    //将用户信息生成一个个元组
    val tups: DStream[(String, Int)] = lines.flatMap(_.split("\\s+")).map((_ , 1))
    //模拟生产谁名单用户
    val blackUser: RDD[(String, Boolean)] =
      sc.sparkContext.makeRDD(List("zhangsan","lisi","wangwu")).map((_, true))
    //用transform对数据进行join和过滤操作
    val goodUserTuple = tups.transform((rdd: RDD[(String, Int)]) => {
      //源数据与黑名单数据进行左连接
      val joinedRDD: RDD[(String, (Int, Option[Boolean]))] = rdd.leftOuterJoin(blackUser)
      //过滤黑名单
      val goodUser = joinedRDD.filter(tup => {
        //拿到是否是黑名单用户的值
        val isBlack: Option[Boolean] = tup._2._2
        if (isBlack.isEmpty) true else false
      })
      //调整数据格式
      goodUser.map(tup => (tup._1, 1))
    })
    //聚合生成有效用户点击量
    val result = goodUserTuple.reduceByKey(_+_)
    result.print()
    sc.start()
    sc.awaitTermination()
  }
}
