package cn.qf.day13.demo06_fileinputoutput.sample02_sequencefile

import cn.qf.entity.Girl
import cn.qf.util.SparkUtil
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SequenceFileDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(SequenceFileDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

//    sc.parallelize(Seq(new Girl("marry", 80.9, 18, 174.6, 50.3),
//      new Girl("lucycat",98.9, 20, 179.1, 48.1),
//      new Girl("Angle",80.9, 21, 172.1, 56.8),
//      new Girl("kate",80.9, 21, 172.1, 56.1),
//      new Girl("lilydo", 80.9, 21, 176.2, 51.5)),1)
//      .map(girl => (girl.getName, JSON.toJSONString(girl,SerializerFeature.UseSingleQuotes)))
//      .saveAsSequenceFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/" +
//        "scala_studying/log/sequence_output")

    sc.sequenceFile[String, String]("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/log/sequence_output/part-00000")
      .map(tuple => JSON.parseObject[Girl](tuple._2,classOf[Girl]))
      .map(girl => {
        girl.setAge(girl.getAge + 2)
        girl//map需要有返回值
      })
      .foreach(println)
    spark.stop()
  }
}
