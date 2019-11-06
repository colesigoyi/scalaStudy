package cn.qf.day12.demo01_action

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonActionDemo2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CommonActionDemo2.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //
    val rdd = sc.parallelize(Seq(3, 4, 5, 1, 0, 9))

    //takeSample(withReplacement,num, [seed]):返回一个数组，
    // 该数组由从数据集中随机采样的num个元素组成，可以选择是否用随机数替换不足的部分，seed用于指定随机数生成器种子
    //随机从rdd中抽取三个元素
    //参数1:true:表示采样后的结果可以重复,false:表示结果不能重复
    //参数2:采样数是一个确切值,与transformation算子的sample不同
    //参数3:随机种子
    println(s"采样后的结果是:${rdd.takeSample(false, 3).toBuffer}")
    println("----------------------")
    //aggregate:使用到了柯里化,有两个形参列表
    //          形参列表1:初始值
    //          形参列表2:两个参数:
    //                    参数1:作用于每个分区
    //                    参数2:作用于全局
    //与transformation的aggregateByKey类似,
    // 不同之处是aggregate是action算子,不要求RDD中每个元素是对偶元组,且作用于全局操作时,初试值也要参与运算
    //需求:各个分区中的最大值,且求和输出
    val rdd2 = sc.parallelize(Seq(3, 4, 5, 1, 0, 9), 2)
    rdd2.mapPartitionsWithIndex((index, itr) => itr.map((_, index))).foreach(println)
//    val result = rdd2.aggregate(6)(math.max(_ , _),_ - _) //-9
//    val result = rdd2.aggregate(6)(math.max(_ , _),_ + _) //21
    val result = rdd2.aggregate(0)(math.max,_ + _) //14
    println(s"结果是:${result}")
    spark.stop()
  }
}
