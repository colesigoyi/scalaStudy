package cn.qf.day04.sample01_trait.demo06_extendclasstrait

/**
 * Description：扩展类的特质<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class MusicProgramStudent extends Student with DoMusic with Programming {
  override def writeSong: Unit = println("作曲")

  override def writeCode: Unit = println("编程")
}
