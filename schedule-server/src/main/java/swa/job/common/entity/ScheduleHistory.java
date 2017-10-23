package swa.job.common.entity;import java.io.Serializable;import java.util.Date;/** * ScheduleHistory:调度历史 * Created by jinyan.cao on 2017-10-23 18:43:09 */public class ScheduleHistory implements Serializable {	private static final long serialVersionUID = 1L;		/**	 *自增主键	 */	private Long id;	/**	 *任务名	 */	private String jobName;	/**	 *调度地址	 */	private String scheduleAddress;	/**	 *调度参数	 */	private String scheduleParam;	/**	 *执行时间	 */	private String executeTime;	/**	 *执行状态：0-未执行，1-失败，2-成功	 */	private Integer executeStatus;	/**	 *创建时间	 */	private Date createTime;	/**	 *更新时间	 */	private Date updateTime;	public ScheduleHistory(String jobName, Integer executeStatus) {		this.jobName = jobName;		this.executeStatus = executeStatus;	}	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public String getJobName() {	    return this.jobName;	}	public void setJobName(String jobName) {	    this.jobName=jobName;	}	public String getScheduleAddress() {	    return this.scheduleAddress;	}	public void setScheduleAddress(String scheduleAddress) {	    this.scheduleAddress=scheduleAddress;	}	public String getScheduleParam() {	    return this.scheduleParam;	}	public void setScheduleParam(String scheduleParam) {	    this.scheduleParam=scheduleParam;	}	public String getExecuteTime() {	    return this.executeTime;	}	public void setExecuteTime(String executeTime) {	    this.executeTime=executeTime;	}	public Integer getExecuteStatus() {	    return this.executeStatus;	}	public void setExecuteStatus(Integer executeStatus) {	    this.executeStatus=executeStatus;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}}