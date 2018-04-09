package swa.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import swa.db.entity.App;
import swa.service.AppService;

import javax.annotation.Resource;


/**
 * ApplicationController  应用表
 * Created by jinyan.cao on 2017-10-23 18:37:54
 */
@Controller
@RequestMapping("/app")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Resource
    private AppService appService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "index", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/app/appIndex");
        modelAndView.addObject("appInfos", appService.queryApp());
        return modelAndView;
    }

    @RequestMapping(value = "appInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView appInfo(@RequestParam("appName") String appName) {
        ModelAndView modelAndView = new ModelAndView("app/appInfo");
        modelAndView.addObject("appInfos", appService.queryApp());
        modelAndView.addObject("appInfo", appService.queryByName(appName));
        logger.info("appInfo:{}",appService.queryByName(appName));
        return modelAndView;

    }

    @RequestMapping("appSave")
    @ResponseBody
    public String appSave(App app) {
        logger.info("appSave:{}", app);
        try {
            appService.update(app);
            return "save success！";
        } catch (Exception e) {
            logger.error("appSave error:", e);
            return "save fail！";
        }

    }


    public static void main(String[] args) {
        App applicationInfo = new App();
        applicationInfo.setAppName("aa");
        applicationInfo.setAddress("127.0.0.1");
        applicationInfo.setPort(8080);
        System.out.println(JSON.toJSONString(Lists.newArrayList(applicationInfo)));
    }



}

