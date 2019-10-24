package cn.qf.day04.sample06_modelmatch.demo06_witchprotectwatch

/**
 * Description：带守卫的匹配模式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WitchProtectWatchDemo {
  def main(args: Array[String]): Unit = {
    val someEmail = Email("10086","傻逼","大傻逼")
    val someSms = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

    val blackList = Seq("10086")

    println(watchNotification(someSms,blackList))
    println(watchNotification(someEmail,blackList))
    println(watchNotification(someVoiceRecording,blackList))
  }
  def watchNotification(notification: Notification,blackList:Seq[String]) = {
    notification match {
      case Email(sender, subject, content) if !blackList.contains(sender) =>
        s"You got an email from $sender with title: $subject,context: $content"
      case SMS(sender, message) =>
        s"You got an SMS from $sender! Message: $message"
      case VoiceRecording(phoneNum, link) =>
        s"you received a Voice Recording from $phoneNum! Click the link to hear it: $link"
      case _ => s"no match"
    }
  }
}

abstract class Notification
case class Email(sender: String, subject: String, content: String) extends Notification
case class SMS(sender: String, message: String) extends Notification
case class VoiceRecording(phoneNum: String, link: String) extends Notification