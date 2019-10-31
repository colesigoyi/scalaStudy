package cn.qf.day07.demo03_netty.a_server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class NettyServer {
  def bind(host:String,port:Int): Unit = {
    // 配置服务器线程组
    // 用于服务器接收客服端连接的
    val group = new NioEventLoopGroup()
    // 用户进行网络读写
    val loopGroup = new NioEventLoopGroup()
    // 是Netty用户启动NIO服务端的辅助类
    val bootstrap = new ServerBootstrap()
    bootstrap.group(group,loopGroup)
      .channel(classOf[NioServerSocketChannel])
      // 绑定i/o事件
      .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(c: SocketChannel): Unit = {
          c.pipeline().addLast(
            new ServerHandler
          )
        }
      })
    // 绑定端口
    val channelFuture = bootstrap.bind(host,port).sync()
    println("服务器启动完毕")
    // 等待服务关闭
    channelFuture.channel().closeFuture().sync()
    // 退出 释放线程
    group.shutdownGracefully()
    loopGroup.shutdownGracefully()
  }
}

object NettyServer {
  def main(args: Array[String]): Unit = {
    val host = "127.0.0.1"
    val port = "8888".toInt
    val server = new NettyServer
    server.bind(host,port)
  }
}

