package cn.qf.day13.demo06_fileinputoutput.sample01_json
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
object JSONFileInputOutputDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(JSONFileInputOutputDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

//    sc.parallelize(Seq(new Girl("marry", 80.9, 18, 174.6, 50.3),
//      new Girl("lucycat",98.9, 20, 179.1, 48.1),
//      new Girl("Angle",80.9, 21, 172.1, 56.8),
//      new Girl("kate",80.9, 21, 172.1, 56.1),
//      new Girl("lilydo", 80.9, 21, 176.2, 51.5)),1)
//      .map(perEle => JSON.toJSONString(perEle,SerializerFeature.UseSingleQuotes))
//      .saveAsTextFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/" +
//        "scala_studying/log/output8")

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/" +
      "scala_studying/log/output8/part-00000")
        .map(perLine => JSON.parseObject[Girl](perLine,classOf[Girl]))
        .map(perEle => {
          perEle.setAge(perEle.getAge+1)
          perEle//map需要有返回值
        })
        .foreach(println)
    spark.stop()
  }
}
