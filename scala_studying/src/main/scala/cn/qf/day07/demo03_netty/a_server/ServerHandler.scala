package cn.qf.day07.demo03_netty.a_server

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

class ServerHandler extends ChannelInboundHandlerAdapter{
  /**
   * 当客户端连接上了server后使用此方法
   * @param ctx
   */
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("连接成功")
    Thread.sleep(2000)
  }

  /**
   * 接收客户端发来的消息
   * @param ctx
   * @param msg
   */
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    println("接收到客户端发来的消息")
    val byteBuf = msg.asInstanceOf[ByteBuf]
    val bytes = new Array[Byte](byteBuf.readableBytes())
    byteBuf.readBytes(bytes)
    val message = new String(bytes,"UTF-8")
    println(message)
    val back = " Client 再见！！！"
    val resp = Unpooled.copiedBuffer(back.getBytes("UTF-8"))
    ctx.write(resp)
  }

  /**
   * 将消息队列汇总的数据写入到SocketChannel 并发送给对方
   */
  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }
}
