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
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.job.JobInfo;
import swa.job.schedule.JobScheduleParser;

/**
 * 向server服务器发送job信息
 */
public class JobInfoSender extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobInfoSender.class);
    private final JobInfo jobInfo;

    public JobInfoSender(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.debug("channelActive：{}", jobInfo);
        JobScheduleParser.ScheduleMsgBody msg = new JobScheduleParser.ScheduleMsgBody();
        msg.setCode("ADD");
        msg.setNewJob(jobInfo);
        ctx.writeAndFlush(JSON.toJSONString(msg));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.debug("channelRead:{}", msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.debug("channelReadComplete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
