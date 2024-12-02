package com.lyh.rpc.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public class VertxHttpServer implements Httpserver {
    @Override
    public void start(int port) {
//        创建vertx实例
        Vertx vertx = Vertx.vertx();
//        创建http 服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();
//监听端口并处理请求
        httpServer.requestHandler(new HttpServerHandler());
//启动HTTP服务器,监听指定的端口,并在启动成功或失败时打印消息
        httpServer.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("HTTP服务器启动成功，监听端口：" + port);
            } else {
                System.out.println("HTTP服务器启动失败：" + result.cause());
            }
        });
    }
}
