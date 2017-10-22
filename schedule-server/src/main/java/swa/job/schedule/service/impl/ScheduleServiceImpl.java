package swa.job.schedule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.job.schedule.dto.ScheduleResponse;
import swa.job.schedule.service.ScheduleService;
import swa.rpc.service.RpcService;

import javax.annotation.Resource;

/**
 * 从库中获取定时任务执行时间，并根据时间发送任务调度请求
 * Created by jinyan on 10/12/17 3:10 PM.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    @Resource
    private RpcService rpcService;

    /**
     * 向client发送调度请求
     *
     * @param jobName
     * @param ipAddr
     * @return
     */
    public ScheduleResponse schedule(String jobName, String ipAddr) {
        String response= rpcService.remoteRequest(jobName, ipAddr);
        return new ScheduleResponse(Boolean.TRUE);
    }
}
