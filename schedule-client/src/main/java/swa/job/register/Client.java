/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package swa.job.register;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import swa.job.DataDecoder;
import swa.job.DataEncoder;
import swa.job.JobInfo;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class Client {
    private String host;
    private int port;
    private String jobInfo;

    public Client(String host, int port, String jobInfo) {
        this.host = host;
        this.port = port;
        this.jobInfo = jobInfo;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DataDecoder());//in
                            ch.pipeline().addLast(new DataEncoder());//out
                            ch.pipeline().addLast(new JobInfoSender(jobInfo));//in
                            ch.pipeline().addLast(new LoggingHandler());
                        }
                    });
            Channel channel = b.connect(host, port).sync().channel();

            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    JobInfo jobInfo = new JobInfo();
                    jobInfo.setJobId(12L);
                    new Client("127.0.0.1", 8087, JSON.toJSONString(jobInfo)).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}
