package cn.qf.day01.sample08_method

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MethodDemo {
  def main(args: Array[String]): Unit = {
    println(s"调用add方法 => 两个数的和是:${add(1,2)}") //3
    println(s"调用add2方法 => 两个数的和是:${add2(1,2)}") //3
    println(s"调用add3方法 => 两个数的和是:${add3(1,2)}") //()
    println(s"调用add4方法 => 两个数的和是:${add4(1,2) _}") //()
    println(s"调用add4方法 => 两个数的和是:${add4(1,2)(3)}") //()
    println(s"JAVA_HOME=${getJavaHome}")
    println(s"JAVA_HOME=${getJavaHome2()}")
    println(s"JAVA_HOME=${getJavaHome2}")
    show(2)
    show(b=3)
    show2(b=100)
    add5(1,2,3,4,5)
    add6(1,2,3,4,5)
    val nums = Array(1,2,3)
    add6(1,nums:_*)
    val a = add2(1, 2)

  }

  /**
   *  求两个数的和
   * @param x
   * @param y
   * @return
   */
  def add(x:Int, y:Int): Int = x + y

  /**
   *  求两个数的和,省略了返回值类型,返回值由方法体重最后一条表达式的类型确定
   * @param x
   * @param y
   * @return
   */
  def add2(x:Int, y:Int) = x + y

  /**
   * sum3:省略了返回值类型和等于号,没有返回值,将默认的Unit返回
   * @param x
   * @param y
   */
  def add3(x:Int, y:Int) {
    x + y
  }

  /**
   * 柯里化:底层在调用多个参数列表的方法时,会依次缩小参数列表的个数,直到最后变成一个参数列表的过程
   * 多个参数列表,每个参数列表有多个参数
   * 定义了带两个参数列表的方法
   * @param x
   * @param y
   * @param z
   * @return
   */
  def add4(x:Int, y:Int)(z: Double) = x + y + z

  /**
   *  无参数的方法,用于获得当前os的环境变量JAVA_HOME的值
   * @return
   */
  def getJavaHome : String = System.getenv("JAVA_HOME")

  /**
   * 无参数的方法，用于获得当前os的环境变量JAVA_HOME的值
   * @return
   */
  def getJavaHome2() : String = System.getenv("JAVA_HOME")

  /**
   * 带有默认值的方法
   * 传入实参时，不指定参数名，默认根据顺序进行匹配
   * @param a
   * @param b
   * @param c
   */
  def show(a:Int = 1, b:Int = 2, c:Int = 3) = println(s"a=$a,b=$b,c=$c")
  def show2(a:Int = 1, b:Int, c:Int = 3) = println(s"a=$a,b=$b,c=$c")

  /**
   * 可变长参数
   * @param a
   */
  def add5(a:Int*): Unit ={
    for(i <- a){
      println(i)
    }
  }

  /**
   * 可变长参数,放在最后
   * @param a
   * @param b
   */
  def add6(a:Int,b:Int*): Unit ={
    println(s"a=$a")
    for(i <- b){
      println(s"b=$i")
    }
  }
}
