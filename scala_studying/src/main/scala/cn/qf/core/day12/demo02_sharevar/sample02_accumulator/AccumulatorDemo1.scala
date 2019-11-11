package cn.qf.core.day12.demo02_sharevar.sample02_accumulator

import cn.qf.util.SparkUtil
import org.apache.spark.util.LongAccumulator

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AccumulatorDemo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(AccumulatorDemo1.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //需求:
    //使用累加器,计算RDD中元素的个数
    val rdd = sc.parallelize(Seq(1,2,3,4))
    //构建累加器实例
    val cntAccumulator = new LongAccumulator
    //进行注册
    sc.register(cntAccumulator)
    //分析rdd.在算子中使用累加器进行计数(在Executor进程中也可读取累加器的值,但是结果是当前Executor中多个线程操作的结果,还没有全局合并
    rdd.map(perEle => {
      cntAccumulator.add(1)
      println(s"Executor累加器实例中累积后的值是:${cntAccumulator.value}")
      /*
      Executor累加器实例中累积后的值是:1
      Executor累加器实例中累积后的值是:1
      Executor累加器实例中累积后的值是:1
      Executor累加器实例中累积后的值是:1
       */
    }).count()
    //在Driver进程中读取累加器的值
    println(s"累加器中最终累积后的值是:${cntAccumulator.value}")  //累加器中最终累积后的值是:4




    spark.stop()
  }
}
