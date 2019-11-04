package cn.qf.day12.demo01_action

import cn.qf.day10.demo01_transformation.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonActionDemo4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CommonActionDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //
    val rdd = sc.parallelize(Seq(("jack", 5),("marry", 6),("jack", 5),("tom", 3)),1)

    //saveAsTextFile(path):将数据集的元素以textfile的形式保存到HDFS文件系统或者其他支持的文件系统，对于每个元素，Spark将会调用toString方法，将它装换为文件中的文本
    rdd.saveAsTextFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/log/outputTest")
    //saveAsSequenceFile(path):将数据集中的元素以Hadoop sequencefile的格式保存到指定的目录下，可以使HDFS或者其他Hadoop支持的文件系统。
    rdd.saveAsSequenceFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/log/outputTest2")

    //saveAsObjectFile(path)
    //java中的api:序列化:将内存中的数据输出到磁盘或网络:ObjectOutputStream
    //           反序列化:读取网络或磁盘内容到内存:ObjectInputStream
    rdd.saveAsObjectFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/log/outputTest3")

  }
}
