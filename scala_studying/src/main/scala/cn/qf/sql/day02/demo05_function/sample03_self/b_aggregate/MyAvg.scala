package cn.qf.sql.day02.demo05_function.sample03_self.b_aggregate

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, IntegerType, StructField, StructType}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
/**
 * 自定义求平均值的方法
 */
class MyAvg extends UserDefinedAggregateFunction{
  /**
   * 定制输入的参数的元数据信息
   * @return
   */
  override def inputSchema: StructType = {
    StructType(Seq(StructField("salary",DoubleType,nullable = false)))
  }

  /**
   * 用来定制最终的虚拟表的元数据信息
   * @return
   */
  override def bufferSchema: StructType = {
    StructType(Seq(
      StructField("sum",DoubleType,nullable = false),
      StructField("mycnt",IntegerType,nullable = false)
    ))}

  /**
   * 自定义函数指定完毕后返回值的类型
   * @return
   */
  override def dataType: DataType = DoubleType

  /**
   * 输入的参数中,是否包含日期型的参数
   * 存在:false
   * 不存在:true
   * @return
   */
  override def deterministic: Boolean = true

  /**
   * 初始化参数指定的共享变量,被所有的线程实例锁共享
   * @param buffer
   */
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0.0
    buffer(1) = 0
  }

  /**
   *合并当前Executor进程所维护的线程池中每个线程所操作的分区值
   * @param buffer
   * @param input
   */
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer(0).asInstanceOf[Double] + input(0).asInstanceOf[Double]
    buffer(1) = buffer(1).asInstanceOf[Int] + 1
  }

  /**
   * 合并所有Executor进程所维护的线程池中每个线程所操作的分区的最终结果
   * @param buffer1
   * @param buffer2:当前分区中最终合并后的结果
   */
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1(0).asInstanceOf[Double] + buffer2(0).asInstanceOf[Double]
    buffer1(1) = buffer1(1).asInstanceOf[Int] + + buffer2(1).asInstanceOf[Int]
  }

  /**
   * 自定义函数返回最终的结果
   * @param buffer
   * @return
   */
  override def evaluate(buffer: Row): Any = {
    buffer(0).asInstanceOf[Double]/buffer(1).asInstanceOf[Int]
  }
}
