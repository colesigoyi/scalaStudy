package cn.qf.day06.demo02_implicit.sample01_class.b_advanced

import java.io.File

import scala.io.Source

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object MyPredef {
  implicit class RichFile(f:File) {
    /**
     * 读取特定文件的内容,并以字符串的形式返回
     * @return
     */
    def read = Source.fromFile(f).mkString
  }
}
