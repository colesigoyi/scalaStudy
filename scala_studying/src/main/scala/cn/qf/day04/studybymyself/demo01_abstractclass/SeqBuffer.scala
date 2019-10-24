package cn.qf.day04.studybymyself.demo01_abstractclass

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

abstract class SeqBuffer extends Buffer {
  type U
  type T <: Seq[U]
  def length = element.length
}
