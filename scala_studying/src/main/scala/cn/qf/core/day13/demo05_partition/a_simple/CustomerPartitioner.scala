package cn.qf.core.day13.demo05_partition.a_simple

import org.apache.spark.Partitioner

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class CustomerPartitioner(num:Int) extends Partitioner{
  /**
   * 获得分区数
   * @return
   */
  override def numPartitions: Int = num

  /**
   * 获得待保存到rdd相应的分区的元素所对应的分区编号
   * @param key
   * @return
   */
  override def getPartition(key: Any): Int = key.hashCode() % num
}
