package cn.qf.scala.day07.demo03_netty.b_client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class NettyClient {

  def bind(host:String,port:Int): Unit = {
    // 配置服务器线程组
    // 用于服务器接收客服端连接的
    val group = new NioEventLoopGroup()
    // 创建客户端的辅助类
    val bootstrap = new Bootstrap

    bootstrap.group(group)
      .channel(classOf[NioSocketChannel])
      // 绑定i/o事件
      .handler(new ChannelInitializer[SocketChannel] {
        override def initChannel(c: SocketChannel): Unit = {
          c.pipeline().addLast(
            new ClientHandler
          )
        }
      })
    // 绑定端口
    val channelFuture = bootstrap.connect(host,port).sync()
    println("客户端启动")
    // 等待服务关闭
    channelFuture.channel().closeFuture().sync()
    // 退出 释放线程
    group.shutdownGracefully()
  }
}

object NettyClient {
  def main(args: Array[String]): Unit = {
    val host = "127.0.0.1"
    val port = "8888".toInt
    val server = new NettyClient
    server.bind(host,port)
  }
}


