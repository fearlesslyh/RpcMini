# RPCmini框架



## 什么是RPC？

专业定义：RPC(Remote Procedure Call)即远程过程调用，是一种计算机通信协议，它允许程序在不同的计算机之间进行通信和交互，就像本地调用一样。

### 举个例子。

假设你住在A城市，你的朋友小明住在B城市。你们经常互相借书看。每次你想借书时，你都会打电话给小明，告诉他你需要哪本书，小明会在他的书架上找到这本书，然后通过快递寄给你。等你看完后，再通过快递还给他。

在这个过程中，你可以把电话通话和快递寄送的过程看作是RPC的一部分：
发起请求：你打电话给小明，告诉他你需要哪本书。这相当于你在本地发起一个RPC请求。
处理请求：小明接到电话后，去他的书架上找到这本书。这相当于远程服务器接收到请求并处理它。返回结果：小明通过快递把书寄给你。这相当于远程服务器返回处理结果。
接收结果：你收到书，开始阅读。这相当于客户端接收到返回的结果并继续执行后续操作。

在计算机网络中，这个过程是通过网络协议（如HTTP、TCP等）实现的。具体步骤如下：

1. 客户端调用：客户端程序调用一个本地方法，这个方法实际上是一个代理（Stub）。
2. 序列化：代理将调用的方法名、参数等信息打包成一个消息，这个过程称为序列化。
3. 发送请求：代理通过网络将消息发送到远程服务器。
4. 反序列化：远程服务器接收到消息后，将其反序列化，还原成方法名和参数。
5. 服务端处理：远程服务器上的服务（Skeleton）调用实际的方法，处理请求。
6. 返回结果：服务端将处理结果打包成消息，通过网络返回给客户端。
7. 反序列化：客户端接收到消息后，将其反序列化，还原成结果。
8. 继续执行：客户端程序接收到结果后，继续执行后续操作。

### 优点

1. 透明性：客户端可以像调用本地方法一样调用远程方法，无需关心网络通信的细节。
2. 解耦：客户端和服务端可以独立开发和部署，降低了系统的耦合度。
3. 可扩展性：可以通过增加更多的服务端节点来提高系统的性能和可用性。

### 缺点

网络延迟：由于涉及到网络通信，可能会有延迟，影响性能。

复杂性：需要处理网络故障、超时等问题，增加了系统的复杂性。

### 应用场景

1. 微服务架构：在微服务架构中，不同的服务之间通过RPC进行通信。
2. 分布式系统：在大型分布式系统中，不同的节点之间通过RPC进行数据交换和协同工作。

## 项目介绍

### 目录

![image-20241130223947576](C:\Users\RAOYAO\AppData\Roaming\Typora\typora-user-images\image-20241130223947576.png)

1. commonMini 公共模块
2. consumerMini 服务消费者模块
3. miniRPC 简易RPC
4. providerMini 服务提供者模块

### 项目依赖

1. Java环境配置>=11
2. maven打包
3. vert.x工具包
4. 具体看源码

### 启动类

服务提供者和消费者都要启动

![image-20241130224547720](C:\Users\RAOYAO\AppData\Roaming\Typora\typora-user-images\image-20241130224547720.png)

![image-20241130224626660](C:\Users\RAOYAO\AppData\Roaming\Typora\typora-user-images\image-20241130224626660.png)

### 故障和错误

- 运行不起来，请查看自己的环境配置，以及maven加载了依赖没有
- 确保加载了vert.x，并成功启动了服务器
- 具体报错，日志发我

## 后续

版本为1.0版本，手搓代码出来的，会不断维护和更新。

如有任何问题或建议，请联系liangyihao@proton.me
