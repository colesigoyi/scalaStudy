package cn.qf.day13.demo05_partition.subject

import org.apache.spark.Partitioner

import scala.collection.mutable

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class MyPartitioner(subjectName:Array[String]) extends Partitioner{
  /**
   * 准备一个容器:用于存储所有的学科名,以及学科对应的分区编号
   * key:学科名
   * value:分区编号
   */
  private val container = mutable.Map[String, Int]()

  for (index <- 0 until subjectName.size) {
    val subjectname = subjectName(index)
    container.put(subjectname, index)
  }

  /**
   * 获得总分区数
   * @return
   */
  override def numPartitions: Int = {
    subjectName.size
  }

  /**
   * 获得分区编号
   * @param key
   * @return
   */
  override def getPartition(key: Any): Int = {
    container.getOrElse(key.toString, 0)
  }
}
