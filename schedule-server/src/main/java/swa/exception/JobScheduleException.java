package swa.exception;

/**
 * Created by jinyan on 10/11/17 9:50 PM.
 */
public class JobScheduleException extends RuntimeException {
    private static final long serialVersionUID = -5327964834790652591L;
    String msg;

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
