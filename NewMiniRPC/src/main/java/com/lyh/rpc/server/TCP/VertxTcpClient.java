package com.lyh.rpc.server.TCP;

import io.vertx.core.Vertx;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class VertxTcpClient {
    public void start() {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8888, "localhost", result -> {
            if (result.succeeded()) {
                System.out.println("连接到TCP服务器");
                io.vertx.core.net.NetSocket socket = result.result();
                // 发送数据
                socket.write("Hello, 服务器!");
                // 接收响应
                socket.handler(buffer -> {
                    System.out.println("从服务器收到响应: " + buffer.toString());
                });
            } else {
                System.err.println("连接TCP服务器失败");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
