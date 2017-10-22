package swa.job.schedule.dto;

/**
 * Created by jinyan on 10/12/17 3:14 PM.
 */
public class ScheduleResponse {
    private Boolean isSuccess;

    public ScheduleResponse() {
    }

    public ScheduleResponse(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "ScheduleResponse{" +
                "isSuccess=" + isSuccess +
                '}';
    }
}
