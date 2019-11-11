package cn.qf.scala.day07.demo01_exercise

import java.io.File

import scala.actors.Future
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MultiThreadWordCountDemo {
  def main(args: Array[String]): Unit = {
    //1.获得特定目录下文件的信息
    //a.准备容器,存储文件的路径
    val fileNames = ArrayBuffer[String]()
    //b.从目录中取出所有文件
    val fileInstance = new File("homeworktest")
    val arr = fileInstance.listFiles()
    //c.存入
    arr.foreach((file => fileNames.append((file.getAbsolutePath))))
    //2.通过循环来分析存储文件信息的容器,每循环一次,启动一个线程,计算当前文件中单词出现的次数,并将返回的结果存入一个容器
    //a.准备容器,用于子线程的返回值
    val tmpResultContainer = ListBuffer[Future[Any]]()
    fileNames.foreach(fileNames => {
      //b.构建线程的实例并启动
      val threadInstance = new Task()
      threadInstance.start()
      //c.向子线程发送带返回值的异步消息
      val returnValue:Future[Any] = threadInstance !! SubmitTask(fileNames)
      //d.将返回值存入到容器中
      tmpResultContainer.append(returnValue)
    })
    //3.将结果容器中的数据拿出来,进行判断,若当前的线程执行完毕,将结果拿出来,置于另一个容器中
    //a.准备容器,用于存储子线程执行完毕后返回的结果
    val overResultContainer = ArrayBuffer[ReturnResult]()
    while (tmpResultContainer.size > 0) {
      //b.判断容器中的每个元素是否执行完毕,取出已经执行完毕的线程
      val tmpContainerMoreOver:ListBuffer[Future[Any]] = tmpResultContainer.filter(_.isSet)
      //c.取出器返回结果存入新的容器中
      tmpContainerMoreOver.foreach(perEle => {
        //取出结果
        val realResult:ReturnResult = perEle.apply().asInstanceOf[ReturnResult]
        //存入容器
        overResultContainer.append(realResult)
        //d.并从旧的临时容器中移除
        tmpResultContainer -= perEle
      })
    }
    //4.分析存储最终结果的容器,统计出最终的结果,并显示输出
    val finalResult = overResultContainer.map(_.result)
      .flatten
      .groupBy(_._1)
      .mapValues(x => x.foldLeft(0)(_ + _._2))
      .toList
      .sortWith(_._2 > _._2)
    finalResult.foreach(perEle => println(perEle._1 + "\t" + perEle._2))
    //5.终止线程,停止当前线程
    sys.exit()
  }
}
