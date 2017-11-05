package swa.job.common;

import swa.db.entity.JobInfo;

/**
 * Created by jinyan on 10/20/17 2:20 PM.
 */
public class JobContext {
    private Long jobId;
    private String appName;
    private String beanName;
    private String methodName;
    private String cronParam;
    private String address;
    private Integer port;
    private String param;

    public JobContext() {
    }

    public JobContext(JobInfo jobInfo, String address, Integer port) {
        this.jobId = jobInfo.getId();
        this.appName = jobInfo.getAppName();
        this.beanName = jobInfo.getBeanName();
        this.methodName = jobInfo.getMethodName();
        this.cronParam = jobInfo.getCronParam();
        this.address = address;
        this.port = port;
        this.param = jobInfo.getParam();
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCronParam() {
        return cronParam;
    }

    public void setCronParam(String cronParam) {
        this.cronParam = cronParam;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "JobContext{" +
                "jobId=" + jobId +
                ", appName='" + appName + '\'' +
                ", beanName='" + beanName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", cronParam='" + cronParam + '\'' +
                ", address='" + address + '\'' +
                ", port=" + port +
                ", param='" + param + '\'' +
                '}';
    }
}
