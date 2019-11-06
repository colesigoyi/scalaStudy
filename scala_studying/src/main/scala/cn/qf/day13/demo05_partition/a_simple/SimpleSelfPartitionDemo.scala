package cn.qf.day13.demo05_partition.a_simple

import cn.qf.util.SparkUtil
import org.apache.spark.rdd
import org.apache.spark.rdd.RDD

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SimpleSelfPartitionDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(SimpleSelfPartitionDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    val rdd = sc.parallelize(1 to 10, 1)
    //使用默认分区
    val rdd2 = rdd.zipWithIndex()//编号不受分区影响,不重复
      rdd2.foreach(println)
    /*
    (1,0)
    (2,1)
    (3,2)
    (4,3)
    (5,4)
    (6,5)
    (7,6)
    (8,7)
    (9,8)
    (10,9)
     */
    println("--------------------------")
    showPartitionDetailInfo(rdd2)
    /*
    当前分区编号:0,元素值是:(1,0)
    当前分区编号:0,元素值是:(2,1)
    当前分区编号:0,元素值是:(3,2)
    当前分区编号:0,元素值是:(4,3)
    当前分区编号:0,元素值是:(5,4)
    当前分区编号:0,元素值是:(6,5)
    当前分区编号:0,元素值是:(7,6)
    当前分区编号:0,元素值是:(8,7)
    当前分区编号:0,元素值是:(9,8)
    当前分区编号:0,元素值是:(10,9)
     */
    println("--------------------------")
    //使用自定义分区
    val rdd3 = rdd2.partitionBy(new CustomerPartitioner(3))
    rdd3.foreach(println)
    /*
    (3,2)
    (2,1)
    (1,0)
    (5,4)
    (6,5)
    (8,7)
    (4,3)
    (9,8)
    (7,6)
    (10,9)
     */
    println("--------------------------")
    showPartitionDetailInfo(rdd3)
    /*
    当前分区编号:0,元素值是:(3,2)
    当前分区编号:2,元素值是:(2,1)
    当前分区编号:1,元素值是:(1,0)
    当前分区编号:2,元素值是:(5,4)
    当前分区编号:0,元素值是:(6,5)
    当前分区编号:1,元素值是:(4,3)
    当前分区编号:2,元素值是:(8,7)
    当前分区编号:1,元素值是:(7,6)
    当前分区编号:0,元素值是:(9,8)
    当前分区编号:1,元素值是:(10,9)
     */
    spark.stop()
  }
  def showPartitionDetailInfo(rdd:RDD[(Int, Long)]) = {
    rdd.mapPartitionsWithIndex((index, itr) => {
      itr.map(perEle => s"当前分区编号:$index,元素值是:$perEle")
    }).foreach(println)
  }
}
