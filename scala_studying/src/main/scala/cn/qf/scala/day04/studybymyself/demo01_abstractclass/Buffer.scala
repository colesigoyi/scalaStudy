package cn.qf.scala.day04.studybymyself.demo01_abstractclass

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
trait Buffer {
  type T
  val element: T
}
