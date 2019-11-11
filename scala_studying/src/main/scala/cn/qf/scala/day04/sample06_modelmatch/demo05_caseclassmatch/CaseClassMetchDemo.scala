package cn.qf.scala.day04.sample06_modelmatch.demo05_caseclassmatch

/**
 * Description：样例类匹配模式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CaseClassMetchDemo {
  def main(args: Array[String]): Unit = {
    val someEmail = Email("10086","傻逼","大傻逼")
    val someSms = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")
    println(watchNotification(someSms))
    println(watchNotification(someEmail))
    println(watchNotification(someVoiceRecording))
  }
  def watchNotification(notification: Notification) = {
    notification match {
      case Email(email, subject, content) =>
        s"You got an email from $email with title: $subject,context: $content"
      case SMS(sender, message) =>
        s"You got an SMS from $sender! Message: $message"
      case VoiceRecording(phoneNum, link) =>
        s"you received a Voice Recording from $phoneNum! Click the link to hear it: $link"
      case _ => s"信息错误"
    }
  }
}

abstract class Notification
case class Email(sender: String, subject: String, content: String) extends Notification
case class SMS(sender: String, message: String) extends Notification
case class VoiceRecording(phoneNum: String, link: String) extends Notification