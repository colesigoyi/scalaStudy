package cn.qf.streaming.day04.transformationfunction.mapwithstateacc

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, State, StateSpec, StreamingContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MapWithStateACCDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(MapWithStateACCDemo.getClass.getSimpleName)
    val context = new SparkContext(conf)
    val sc = new StreamingContext(context, Seconds(5))

    sc.checkpoint("hdfs://ns1/spark-streaming/myself/checkpoint_20191114_2/")

    //获取数据
    val lines = sc.socketTextStream("NODE01", 6666)
    //处理数据
    val tups = lines.flatMap(_.split("\\s+")).map((_, 1))

    //spec: StateSpec[K,V, StateType, MappedType]
    //参数中的statespec类中的function方法就是用于指定操作中的各个类型和具体实现逻辑
    //function方法需要的函数
    //(KeyType, Option[ValueType], State[stateType]) => MappedType
    val value = tups.mapWithState(StateSpec.function(func))

    value.print()
    //mapWithState只会拿到相同key的历史状态值

    sc.start()
    sc.awaitTermination()
  }

  /**
   * //(KeyType, Option[ValueType], State[stateType]) => MappedType
   * 参数1:key(单词)
   * 参数2:当前批次的value
   * 参数3:指历史批次value
   * @return
   */
  def func: (String, Option[Int], State[Int]) => (String, Int) = (
               word: String, currentValue: Option[Int], historyState: State[Int]) => {
    val sum = currentValue.getOrElse(0) + historyState.getOption().getOrElse(0)
    val output = (word, sum)
    //将刚才的最终聚合值更新到状态信息中,因为mapWithState拿到的是上一个批次的状态值
    historyState.update(sum)
    output
  }
}
