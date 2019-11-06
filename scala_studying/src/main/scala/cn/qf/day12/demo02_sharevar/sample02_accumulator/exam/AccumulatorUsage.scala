package cn.qf.day12.demo02_sharevar.sample02_accumulator.exam

import cn.qf.util.SparkUtil
import org.apache.spark.util.LongAccumulator

/**
 * Description：累加器使用案例<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AccumulatorUsage {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(AccumulatorUsage.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //考试题:
    //统计文件中包含a和包含b的字符的有几行
    //1.定义2个累加器实例
    val aAccumulator = new LongAccumulator
    val bAccumulator = new LongAccumulator
    //进行注册
    sc.register(aAccumulator)
    sc.register(bAccumulator)
    //2,分析文件,将包含对应字符的语句进行累加
    sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/log/testdata.txt")
        .foreach(perEle => {
          //判断包含a
          if (perEle.contains("a"))
            aAccumulator.add(1)
          //判断包含bÅ
          if (perEle.contains("b"))
            bAccumulator.add(1)
        })
    //在Driver中读取累加器的值
    println(s"包含a的行数:${aAccumulator.value},包含b的行数:${bAccumulator.value}")
    spark.stop()
  }
}
