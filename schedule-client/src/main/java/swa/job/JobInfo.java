package swa.job;

/**
 * Created by jinyan on 10/20/17 2:20 PM.
 */
public class JobInfo {
    private Long jobId;
    private String appName;
    private String beanName;
    private String methodName;
    private String cronParam;
    private String address;
    private Integer port;
    private String param;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobInfo jobInfo = (JobInfo) o;

        if (jobId != null ? !jobId.equals(jobInfo.jobId) : jobInfo.jobId != null) return false;
        if (appName != null ? !appName.equals(jobInfo.appName) : jobInfo.appName != null) return false;
        if (beanName != null ? !beanName.equals(jobInfo.beanName) : jobInfo.beanName != null) return false;
        if (methodName != null ? !methodName.equals(jobInfo.methodName) : jobInfo.methodName != null) return false;
        if (cronParam != null ? !cronParam.equals(jobInfo.cronParam) : jobInfo.cronParam != null) return false;
        if (address != null ? !address.equals(jobInfo.address) : jobInfo.address != null) return false;
        if (port != null ? !port.equals(jobInfo.port) : jobInfo.port != null) return false;
        return param != null ? param.equals(jobInfo.param) : jobInfo.param == null;
    }

    @Override
    public int hashCode() {
        int result = jobId != null ? jobId.hashCode() : 0;
        result = 31 * result + (appName != null ? appName.hashCode() : 0);
        result = 31 * result + (beanName != null ? beanName.hashCode() : 0);
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        result = 31 * result + (cronParam != null ? cronParam.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (param != null ? param.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobInfo{" +
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
