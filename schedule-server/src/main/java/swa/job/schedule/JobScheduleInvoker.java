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
package swa.job.schedule;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 向client请求任务调度
 */
public class JobScheduleInvoker extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleInvoker.class);
    private String jobInfo;

    /**
     * Creates a client-side handler.
     */
    public JobScheduleInvoker(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    //发送调度信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(jobInfo);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("getData:{}", msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
