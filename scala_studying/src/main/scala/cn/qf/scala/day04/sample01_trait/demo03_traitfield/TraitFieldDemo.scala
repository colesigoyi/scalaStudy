package cn.qf.scala.day04.sample01_trait.demo03_traitfield

/**
 * Description：特质中的普通字段和抽象字段<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TraitFieldDemo extends App{
  println(s"访问实现类中重写后的普通常量属性MAX_LEN=${new ConsoleLogger().MAX_LEN}")
}
