package swa.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import swa.db.entity.JobInfo;
import swa.db.service.JobManagerService;
import swa.db.service.JobScheduleService;
import swa.job.common.JobContext;
import swa.job.schedule.JobExecutor;
import swa.rpc.Client;

import javax.annotation.Resource;


/**
 * JobController  任务表
 * Created by jinyan.cao on 2017-10-23 18:37:55
 */
@Controller
@RequestMapping("job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Resource
    private JobManagerService jobManagerService;
    @Resource
    private JobExecutor scheduleExecutor;
    @Resource
    private JobScheduleService jobScheduleService;


    @RequestMapping("/executeNow")
    @ResponseBody
    public String scheduleJob(@RequestParam("jobId") Long jobId) {
        JobContext jobContext = jobManagerService.getExecuteJobInfo(jobId);
        String jsonStr = JSON.toJSONString(jobContext);
        Client client = new Client(jobContext.getAddress(), jobContext.getPort(), jsonStr);
        try {
            client.start();
        } catch (InterruptedException e) {
            logger.error("scheduleJob error:", e);
        }
        jobScheduleService.addJobScheduleHistory(jobContext);
        return "submit success";
    }

    @RequestMapping("/modifyJobInfo")
    @ResponseBody
    public String modifyJobInfo(@RequestParam("jobInfo") JobInfo jobInfo) {
        //保存信息到数据库
        jobManagerService.saveJobInfo(jobInfo);
        //向client端发送调度请求
        scheduleExecutor.sendJob(jobInfo.getId());
        return "success";
    }

    public static void main(String[] args) {
        JobInfo applicationInfo=new JobInfo();
        applicationInfo.setAppName("aa");
        applicationInfo.setPort(8080);
        System.out.println(JSON.toJSONString(Lists.newArrayList(applicationInfo)));
    }
}

