package swa.exception;

/**
 * Created by jinyan on 10/31/17 5:49 PM.
 */
public class PreconditionUtil {
    public static void check(Boolean expression, String msg) {
        if (!expression) {
            throw new JobScheduleException(msg);
        }
    }
}
