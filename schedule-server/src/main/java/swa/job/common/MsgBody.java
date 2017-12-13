package swa.job.common;

/**
 * Created by jinyan on 12/12/17 9:41 PM.
 */
public class MsgBody {
    //标示：ADD，MODIFY
    private String code;
    private JobContext newJob;
    private JobContext oldJob;

    public JobContext getNewJob() {
        return newJob;
    }

    public void setNewJob(JobContext newJob) {
        this.newJob = newJob;
    }

    public JobContext getOldJob() {
        return oldJob;
    }

    public void setOldJob(JobContext oldJob) {
        this.oldJob = oldJob;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MsgBody{");
        sb.append("code='").append(code).append('\'');
        sb.append(", newJob=").append(newJob);
        sb.append(", oldJob=").append(oldJob);
        sb.append('}');
        return sb.toString();
    }
}
