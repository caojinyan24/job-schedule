任务调度系统  
client：127.0.0.1:8080  
server：127.0.0.1:8087


设计思路:
本系统分为client端和server端,其中client端供服务调用方引入,server端处理调度任务的注册和调度
服务调用方在使用调度服务时,引入client的jar包后,client模块会在调用方启动时将调用方的ip地址,配置的端口号,应用名,以及调度服务的类名,方法名通过netty发送到本服务的server端.
server端接收到服务的注册信息后,将服务注册信息保存在数据库.

当一个调度任务需要被调度时,server端将调度的相关信息通过netty服务发送到服务调用者,此时client端将接收到调度信息,并触发调度任务的执行.

#2018年02月23日

优化思路:
* 服务调用方信息注册
client端将服务调用方的ip信息保存在zookeeper
server端监听指定路径的zookeeper节点变化
当调度任务需要被触发时,服务端从zookeeper中获取服务调用者的ip信息,并向调用者发送调度请求

* 任务调度
使用quartz做任务调度管理
将手工触发和自动触发的调度管理区分开,对于自动触发的任务,在client端管理(目前所有的任务触发在server端)
