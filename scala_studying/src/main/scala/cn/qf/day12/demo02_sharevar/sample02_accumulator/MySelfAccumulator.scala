package cn.qf.day12.demo02_sharevar.sample02_accumulator

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable.ListBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class MySelfAccumulator extends AccumulatorV2[String,ListBuffer[String]]{
  private val container = ListBuffer[String]()

  /**
   * 判断当前累加器实例中的属性container是否为空
   * @return
   */
  override def isZero: Boolean = container.isEmpty

  /**
   * 将当前累加器的实例拷贝一份到别的线程实例中,每个线程实例负责累积自己的rdd分区中的元素
   * @return
   */
  override def copy(): AccumulatorV2[String, ListBuffer[String]] = {
    new MySelfAccumulator
  }

  /**
   * 将拷贝过去的累加器实例中的属性container置为空
   */
  override def reset(): Unit = container.clear

  /**
   * 线程池里每累加一次,下述方法调用一次
   * @param v
   */
  override def add(v: String): Unit = container.append(v)

  /**
   * 将所有Executor进程所维护的线程池中线程实例累积后的结果合并起来
   * @param other
   */
  override def merge(other: AccumulatorV2[String, ListBuffer[String]]): Unit = this.container++=other.asInstanceOf[MySelfAccumulator].container

  /**
   * 获取当前累加器实例累积后的结果
   * @return
   */
  override def value: ListBuffer[String] = this.container
}
