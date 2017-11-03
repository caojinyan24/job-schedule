package swa.controller;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swa.db.entity.JobInfo;
import swa.db.service.JobManagerService;
import swa.job.common.JobContext;
import swa.job.schedule.JobExecutor;
import swa.rpc.Client;

import javax.annotation.Resource;


/**
 * JobController  任务表
 * Created by jinyan.cao on 2017-10-23 18:37:55
 */
@Controller
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Resource
    private JobManagerService jobManagerService;
    @Resource
    private JobExecutor scheduleExecutor;


    @RequestMapping("/executeNow")
    public String scheduleJob(@RequestParam("jobId") Long jobId) {
        JobContext jobContext = jobManagerService.getExecuteJobInfo(jobId);
        String jsonStr = JSON.toJSONString(jobContext);
        Client client = new Client(jobContext.getAddress(), jobContext.getPort(), jsonStr);
        try {
            client.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "submit success";
    }

    @RequestMapping("/modifyJobInfo")
    public String modifyJobInfo(@RequestParam("jobInfo") JobInfo jobInfo) {
        //保存信息到数据库
        jobManagerService.saveJobInfo(jobInfo);
        //向client端发送调度请求
        scheduleExecutor.sendJob(jobInfo.getId());
        return "success";
    }


}

