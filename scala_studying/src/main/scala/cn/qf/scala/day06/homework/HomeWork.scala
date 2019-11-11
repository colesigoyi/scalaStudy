package cn.qf.scala.day06.homework

import java.io.File

import scala.actors.Future
import scala.collection.mutable


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object HomeWork {
  def main(args: Array[String]): Unit = {

    val replaySet = new mutable.HashSet[Future[Any]]
    val resultList = new mutable.ListBuffer[ResultTask]

    for (f <- subDir(new File("homeworktest"))) {
      val t = new Task
      val replay = t.start() !! SubmitTask(f.getPath)
      replaySet += replay
    }
    while(replaySet.size > 0){
      val toCumpute = replaySet.filter(_.isSet)//判断是否执行完毕
      for(r <- toCumpute){
        val result = r.apply()
        resultList += result.asInstanceOf[ResultTask]
        replaySet.remove(r)
      }
      Thread.sleep(100)
    }
    val finalResult = resultList.map(_.result)
      .flatten
      .groupBy(_._1)
      .mapValues(x => x.foldLeft(0)(_ + _._2))
      .toList
      .sortWith(_._2 > _._2)
    finalResult.foreach(perEle => println(perEle._1 + "\t" + perEle._2))
    //5.终止线程,停止当前线程
    sys.exit()
  }
  def subDir(dir:File):Iterator[File] ={
    val dirs = dir.listFiles().filter(_.isDirectory())
    val files = dir.listFiles().filter(_.isFile())
    files.toIterator ++ dirs.toIterator.flatMap(subDir _)
  }
}
