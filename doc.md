http2.0,http1.1
怎么做进程间通信

netty的handler用来不同的协议处理数据，其中ChannelInBoundHanlder接收信息，并可返回响应，用来处理业务逻辑
ChannelInitiallizer用来添加ChannelHandler的实现到ChannelPipeline,ChannelInitaillizer本身也是一个Handler，当添加一个Handler到pipeline之后，这个ChannelInitial会自动移除
ChannelPipeLine，EventLoop，EventLoopGroup：事件和事件处理。channle处理socket连接，eventloop处理io，可以看做为一个channel处理工作的线程
channelfulter用来注册一个Listener，用来处理操作结果的回调

inboundHandler和outboundHandler
encoder：将请求的对象转换为字节码。decoder将字节码转换为对象

？？为什么添加的encoder和decoder没有调到（添加Handler的时候要先加Decoder和encoder，最后加业务Handler）；若server无法接收到请求，检查client发送请求的格式和encoder，decoder的实现（网络传输只能传输字节码）

The matching wildcard is strict, but no declaration can be found
添加注解的问题
schedule-client 打ｊａｒ包的时候，无法把META-INF下的文件打进入。代码，配置各种尝试，最后没办法，试着把idea右侧的profiles中的lcoal勾选去掉，居然可以了。。。无法理解为什么勾选上就无法把资源文件打入到jar包中

client跟server用的Server类放在了一个包里，导致一个serverclass文件被覆盖，启动的时候，启动了server模块中的Server类，导致client模块的server无法启动。坑，把client模块对server模块的依赖去掉后问题解决

莫名奇妙的问题：跑单测的时候，报错：找不到指定的bean
最终发现问题出错的原因是加载配置文件的时候，使用了classpath*,修改成classpath之后，问题单测可正常运行。查了下，classpath和classpath*还时有区别的。

还有个无故好了的问题，之前在控制台执行maven的打包命令报各种依赖插件找不到，今天重新试了下居然好了;而且现在勾选上localprofile，打出的jar包中也包含了resources的文件


client模块获得applicationcontext再次莫名失败，调用getbean的时候报空指针:问题原因：当调用到这个方法时，spring还未完成加载，所以获取到null；而在getbean时，需要获取synchronize锁，所以阻塞线程，spring一直未能完成加载，所以一直获取到null。首先调用修改为异步调用，不能让job启动的动作阻塞线程，影响spring的后续加载，一旦加载完成，这个job就可以正常启动



接下来又要开始页面部分了，



client绑定inboundHandler

页面删除无用部分
当调用系统重启时，无法恢复任务的重新执行

写单测的问题：
Process finished with exit code 1
       Class not found: "swa.job.schedule.JobExecutorTest"Empty test suite.
看stackoverflow的解释，跟idea版本有关系，我最开始是用2017.2.5版本出现的这个问题，换成15.0.2的就好了。。。