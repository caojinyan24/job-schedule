//package swa.controller;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import swa.job.common.entity.Application;
//
//
///**
// * ApplicationController  应用表
// * Created by jinyan.cao on 2017-10-23 18:37:54
// */
//@Controller
//@RequestMapping("/application")
//public class ApplicationController  {
//
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
//    @Autowired
//    private ApplicationService applicationService;
//
//    /**
//     * 列表页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "applicationIndex", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView applicationIndex(@ModelAttribute Application query,
//                                         @RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
//                                         @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
//        PageQuery<Application> pageQuery = new PageQuery<Application>();
//        pageQuery.setPageNo(pageNo);
//        pageQuery.setPageSize(pageSize);
//        ModelAndView mav = new ModelAndView();
//        pageQuery.setQuery(query);
//        mav.addObject("query", query);
//        mav.setViewName("manage/applicationIndex");
//        mav.addObject("pageInfos", SystemTools.convertPaginatedList(applicationService.queryApplicationPageList(pageQuery)));
//        return mav;
//    }
//
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
//
//
//}
//
