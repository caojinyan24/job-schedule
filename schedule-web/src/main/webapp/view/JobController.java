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
import swa.manage.entity.Job;
import swa.manage.service.JobService;
import com.qunar.pay.g2.utils.common.log.Logger;
import com.qunar.pay.g2.utils.common.log.LoggerFactory;

 /**
 * JobController  任务表
 * Created by jinyan.cao on 2017-10-23 18:37:55
 */
@Controller
@RequestMapping("/job")
public class JobController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
  @Autowired
  private JobService jobService;
  
 /**
  * 列表页面
  * @return
  */
@RequestMapping(value="jobIndex",method = {RequestMethod.GET,RequestMethod.POST})
public ModelAndView jobIndex(@ModelAttribute Job query,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
	 	PageQuery<Job> pageQuery = new PageQuery<Job>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	ModelAndView mav = new ModelAndView();
		pageQuery.setQuery(query);
		mav.addObject("query",query);
		mav.setViewName("manage/jobIndex");
		mav.addObject("pageInfos",SystemTools.convertPaginatedList(jobService.queryJobPageList(pageQuery)));
		return mav;
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="jobDetail",method = RequestMethod.GET)
public ModelAndView jobDetail(@RequestParam(required = true, value = "id" ) Long id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manage/jobDetail");
		Job job = jobService.queryByPriKey(id);
		mav.addObject("job",job);
		return mav;
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAddDialog",method ={RequestMethod.GET, RequestMethod.POST})
public String toAddDialog(HttpServletRequest request,ModelMap model){
    return "manage/jobAddDialog";
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/jobAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson jobAdd(@ModelAttribute Job job){
	AjaxJson j = new AjaxJson();
	try {
		jobService.add(job);
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
		mav.setViewName("manage/jobEditDialog");
		Job job = jobService.queryByPriKey(id);
		mav.addObject("job",job);
		return mav;
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/jobEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson jobEdit(@ModelAttribute Job job){
	AjaxJson j = new AjaxJson();
	try {
		jobService.update(job);
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
@RequestMapping(value="jobDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson jobDelete(@RequestParam(required = true, value = "id" ) Long id){
		AjaxJson j = new AjaxJson();
		try {
			jobService.deleteByPriKey(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}
