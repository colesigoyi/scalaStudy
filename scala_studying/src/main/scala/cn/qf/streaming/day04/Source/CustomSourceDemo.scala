package cn.qf.streaming.day04.Source


import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket
import java.nio.charset.StandardCharsets

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
/**
 * 自定义数据源
 * @param host
 * @param port
 */
class CustomSourceDemo(host: String, port:Int)
  extends Receiver[String](StorageLevel.MEMORY_AND_DISK){
  /**
   * 在启动的时候被调用一次,此时用该方法实现读取数据并将数据发送给Streaming
   * 生命周期方法
   */
  override def onStart(): Unit = {
    new Thread("Socket Receiver") {
      override def run(): Unit = {
        receive()
      }
    }.start()
  }
  //读取数据并发送给Streaming
  def receive() {
    //创建一个socket
    val socket = new Socket(host, port)
    //定义一个变量,用来接收传过来的数据
    var input:String = null
    //创建一个BufferReader用于读取端口传过来的数据
    val reader = new BufferedReader(new InputStreamReader(socket.getInputStream, StandardCharsets.UTF_8))
    //读取数据
    input = reader.readLine()
    //当Receiver没有关闭且输入数据不为空,则循环发送数据到streaming
    while(!isStopped() && input != null) {
      //如果接受到就保存数据
      store(input)
      //重新读取其他数据
      input = reader.readLine()
    }
    //跳出循环则关闭资源
    reader.close()
    socket.close()

    //重新执行onStart方法,参数需要一个名称
    restart("restart")
  }

  /**
   *程序停止方法
   */
  override def onStop(): Unit = {}
}

object CustomSourceDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(CustomSourceDemo.getClass.getSimpleName)
    val context = new SparkContext(conf)
    val sc = new StreamingContext(context, Durations.milliseconds(5000))
    //创建自定义数据源的Receiver
    val lines = sc.receiverStream(new CustomSourceDemo("NODE01",6666))
    //处理数据
    val res = lines.flatMap(_.split("\\s+")).map((_, 1)).reduceByKey(_ + _)

    res.print()

    sc.start()
    sc.awaitTermination()
  }
}
