package cn.qf.scala.day03.studybymyself.demo01_class

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Outer {
  class Inner {
    private def f(): Unit = {
      println("inner.f")
    }
    class InnerMost {
      f() // InnerMost 里访问 f 就没有问题的，因为这个访问包含在 Inner 类之内
    }
  }
  //(new Inner).f()
}
