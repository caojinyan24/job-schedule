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
            return "更新成功";
        } catch (Exception e) {
            logger.error("appSave error:", e);
            return "更新失败";
        }

    }


    public static void main(String[] args) {
        App applicationInfo = new App();
        applicationInfo.setAppName("aa");
        applicationInfo.setAddress("127.0.0.1");
        applicationInfo.setPort(8080);
        System.out.println(JSON.toJSONString(Lists.newArrayList(applicationInfo)));
    }

//    /**
//     * 详情
//     *
//     * @return
//     */
//    @RequestMapping(value = "applicationDetail", method = RequestMethod.GET)
//    public ModelAndView applicationDetail(@RequestParam(required = true, value = "id") Long id) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("manage/applicationDetail");
//        Application application = applicationService.queryByPriKey(id);
//        mav.addObject("application", application);
//        return mav;
//    }
//
//    /**
//     * 跳转到添加页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/toAddDialog", method = {RequestMethod.GET, RequestMethod.POST})
//    public String toAddDialog(HttpServletRequest request, ModelMap model) {
//        return "manage/applicationAddDialog";
//    }
//
//    /**
//     * 保存信息
//     *
//     * @return
//     */
//    @RequestMapping(value = "/applicationAdd", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public AjaxJson applicationAdd(@ModelAttribute Application application) {
//        AjaxJson j = new AjaxJson();
//        try {
//            applicationService.add(application);
//            j.setMsg("保存成功");
//        } catch (Exception e) {
//            j.setSuccess(false);
//            j.setMsg("保存失败");
//        }
//        return j;
//    }
//
//    /**
//     * 跳转到编辑页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "toEditDialog", method = RequestMethod.GET)
//    public ModelAndView toEditDialog(@RequestParam(required = true, value = "id") Long id) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("manage/applicationEditDialog");
//        Application application = applicationService.queryByPriKey(id);
//        mav.addObject("application", application);
//        return mav;
//    }
//
//    /**
//     * 编辑
//     *
//     * @return
//     */
//    @RequestMapping(value = "/applicationEdit", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public AjaxJson applicationEdit(@ModelAttribute Application application) {
//        AjaxJson j = new AjaxJson();
//        try {
//            applicationService.update(application);
//            j.setMsg("编辑成功");
//        } catch (Exception e) {
//            j.setSuccess(false);
//            j.setMsg("编辑失败");
//        }
//        return j;
//    }
//
//
//    /**
//     * 删除
//     *
//     * @return
//     */
//    @RequestMapping(value = "applicationDelete", method = RequestMethod.GET)
//    @ResponseBody
//    public AjaxJson applicationDelete(@RequestParam(required = true, value = "id") Long id) {
//        AjaxJson j = new AjaxJson();
//        try {
//            applicationService.deleteByPriKey(id);
//            j.setMsg("删除成功");
//        } catch (Exception e) {
//            j.setSuccess(false);
//            j.setMsg("删除失败");
//        }
//        return j;
//    }


}

