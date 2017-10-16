package swa.exception;

/**
 * Created by jinyan on 10/11/17 9:50 PM.
 */
public class JobScheduleException extends Exception {
    String msg;

    public JobScheduleException() {
        super();
    }

    public JobScheduleException(String message) {
        super(message);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JobScheduleException{" +
                "msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
