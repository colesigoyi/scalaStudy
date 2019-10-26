package cn.qf.day05.demo05_threequoat

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ThreeQuoatDemo {
  def main(args: Array[String]): Unit = {
    val sql =
      """
        |select
        | name,
        | age,
        | address
        |from tb_student
        |where age >= 18
        |""".stripMargin
    println(sql)
  }
}
