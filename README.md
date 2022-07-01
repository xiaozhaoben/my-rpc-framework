# my-rpc-framework

#### 介绍
RPC框架

my-rpc-framework是一款基于nacos实现的rpc框架

#### 特点
* 实现了基于 Java 原生 Socket 传输与 Netty 传输两种网络传输方式
* 实现了两种序列化算法，Json 方式、Kryo 算法
* 实现了两种负载均衡算法：随机算法与轮转算法
* 使用 Nacos 作为注册中心，管理服务提供者信息
* 消费端如采用 Netty 方式，会复用 Channel 避免多次连接
* 接口抽象良好，模块耦合度低，网络传输、序列化器、负载均衡算法可配置
* 实现自定义的通信协议
* 基于注解实现服务注册

项目模块概览
~~~~
roc-api —— 通用接口
rpc-common —— 公用类
rpc-core —— 核心实现模块
test-client —— 测试用消费侧
test-server —— 测试用提供侧
~~~~

参考项目:https://github.com/CN-GuoZiyang/My-RPC-Framework  
https://gitee.com/SnailClimb/guide-rpc-framework