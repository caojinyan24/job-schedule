package swa.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import swa.db.entity.JobInfo;
import swa.db.service.JobManagerService;
import swa.job.schedule.JobExecutor;
import swa.service.AppService;
import swa.service.JobScheduleHistoryService;
import swa.service.JobService;

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
    private JobService jobService;
    @Resource
    private JobScheduleHistoryService jobScheduleHistoryService;
    @Resource
    private AppService appService;

    @RequestMapping("/jobs")
    public ModelAndView jobs(@RequestParam("appName") String appName) {
        ModelAndView result = new ModelAndView("/job/jobs");
        result.addObject("appName", appName);
        result.addObject("jobs", jobService.queryByAppName(appName));
        result.addObject("appInfos", appService.queryApp());// TODO: 11/28/17 考虑优化，不要每次都加入返回数据中 

        return result;
    }

    @RequestMapping("/jobInfo")
    public ModelAndView jobInfo(@RequestParam("jobId") Long jobId) {
        ModelAndView result = new ModelAndView("/job/jobInfo");
        result.addObject("job", jobService.queryById(jobId));
        result.addObject("appInfos", appService.queryApp());// TODO: 11/28/17 考虑优化，不要每次都加入返回数据中
        return result;
    }

    @RequestMapping("/scheduleHistory")
    public ModelAndView scheduleHistory(@RequestParam("jobId") Long jobId) {
        ModelAndView result = new ModelAndView("/job/scheduleHistory");
        result.addObject("histories", jobScheduleHistoryService.queryByJobId(jobId));
        result.addObject("appInfos", appService.queryApp());// TODO: 11/28/17 考虑优化，不要每次都加入返回数据中
        return result;
    }

    @RequestMapping("/executeNow")
    @ResponseBody
    public String scheduleJob(@RequestParam("jobId") Long jobId) {
        //向client端发送调度请求
        scheduleExecutor.sendJob(jobId);
        return "submit success!";
    }

    @RequestMapping("/modifyJobInfo")
    @ResponseBody
    public String modifyJobInfo(JobInfo jobInfo) {
        logger.info("modifyJobInfo:{}", jobInfo);
        try {
            //保存信息到数据库
            jobManagerService.saveJobInfo(jobInfo);

            return "save success!";
        } catch (Exception e) {
            logger.info("modifyJobInfo error:", e);
            return "save fail!";
        }
    }
//
//    public static void main(String[] args) {
//        JobInfo applicationInfo = new JobInfo();
//        applicationInfo.setAppName("aa");
//        applicationInfo.setPort(8080);
//        System.out.println(JSON.toJSONString(Lists.newArrayList(applicationInfo)));
//    }
}

