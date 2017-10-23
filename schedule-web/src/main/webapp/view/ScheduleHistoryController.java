package swa.web.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qunar.pay.g2.fpp.common.utils.AjaxJson;
import com.qunar.pay.g2.fpp.common.utils.PageQuery;
import com.qunar.pay.g2.fpp.common.utils.SystemTools;
import swa.manage.entity.ScheduleHistory;
import swa.manage.service.ScheduleHistoryService;
import com.qunar.pay.g2.utils.common.log.Logger;
import com.qunar.pay.g2.utils.common.log.LoggerFactory;

 /**
 * ScheduleHistoryController  调度历史
 * Created by jinyan.cao on 2017-10-23 18:37:55
 */
@Controller
@RequestMapping("/scheduleHistory")
public class ScheduleHistoryController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ScheduleHistoryController.class);
  @Autowired
  private ScheduleHistoryService scheduleHistoryService;
  
 /**
  * 列表页面
  * @return
  */
@RequestMapping(value="scheduleHistoryIndex",method = {RequestMethod.GET,RequestMethod.POST})
public ModelAndView scheduleHistoryIndex(@ModelAttribute ScheduleHistory query,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
	 	PageQuery<ScheduleHistory> pageQuery = new PageQuery<ScheduleHistory>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	ModelAndView mav = new ModelAndView();
		pageQuery.setQuery(query);
		mav.addObject("query",query);
		mav.setViewName("manage/scheduleHistoryIndex");
		mav.addObject("pageInfos",SystemTools.convertPaginatedList(scheduleHistoryService.queryScheduleHistoryPageList(pageQuery)));
		return mav;
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="scheduleHistoryDetail",method = RequestMethod.GET)
public ModelAndView scheduleHistoryDetail(@RequestParam(required = true, value = "id" ) Long id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manage/scheduleHistoryDetail");
		ScheduleHistory scheduleHistory = scheduleHistoryService.queryByPriKey(id);
		mav.addObject("scheduleHistory",scheduleHistory);
		return mav;
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAddDialog",method ={RequestMethod.GET, RequestMethod.POST})
public String toAddDialog(HttpServletRequest request,ModelMap model){
    return "manage/scheduleHistoryAddDialog";
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/scheduleHistoryAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson scheduleHistoryAdd(@ModelAttribute ScheduleHistory scheduleHistory){
	AjaxJson j = new AjaxJson();
	try {
		scheduleHistoryService.add(scheduleHistory);
		j.setMsg("保存成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEditDialog",method = RequestMethod.GET)
public ModelAndView toEditDialog(@RequestParam(required = true, value = "id" ) Long id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manage/scheduleHistoryEditDialog");
		ScheduleHistory scheduleHistory = scheduleHistoryService.queryByPriKey(id);
		mav.addObject("scheduleHistory",scheduleHistory);
		return mav;
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/scheduleHistoryEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson scheduleHistoryEdit(@ModelAttribute ScheduleHistory scheduleHistory){
	AjaxJson j = new AjaxJson();
	try {
		scheduleHistoryService.update(scheduleHistory);
		j.setMsg("编辑成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
	return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="scheduleHistoryDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson scheduleHistoryDelete(@RequestParam(required = true, value = "id" ) Long id){
		AjaxJson j = new AjaxJson();
		try {
			scheduleHistoryService.deleteByPriKey(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

