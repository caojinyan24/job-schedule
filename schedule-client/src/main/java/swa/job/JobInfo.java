package swa.job;

/**
 * Created by jinyan on 10/20/17 2:20 PM.
 */
public class JobInfo {
    private String appName;
    private String beanName;
    private String methodName;
    private Integer clientPort;

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

    public void setClientPort(Integer clientPort) {
        this.clientPort = clientPort;
    }
}
