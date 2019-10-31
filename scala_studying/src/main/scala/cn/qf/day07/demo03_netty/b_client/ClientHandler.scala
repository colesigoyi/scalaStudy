package cn.qf.day07.demo03_netty.b_client

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class ClientHandler extends ChannelInboundHandlerAdapter{


  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("发送消息请求")
    val content = "Hello Server"
    ctx.writeAndFlush(Unpooled.copiedBuffer(content.getBytes("UTF-8")))
  }

  //客户端读取来自服务端反馈的信息
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    println("接收到server发来的消息")
    val byteBuf = msg.asInstanceOf[ByteBuf]
    val bytes = new Array[Byte](byteBuf.readableBytes())
    byteBuf.readBytes(bytes)
    val message = new String(bytes,"UTF-8")
    println(message)
  }

  /**
   * 将消息队列汇总的数据写入到SocketChannel 并发送给对方
   */
  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    // 是否循环
    // channelActive(ctx)
    ctx.flush()
  }
}
