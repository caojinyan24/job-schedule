package swa.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 任务调度状态
 * Created by jinyan on 12/8/17 10:42 AM.
 */
public enum ScheduleStatusEnum {
    INIT(0, "未调度"), FAIL(1, "失败"), SUCCESS(2, "成功");

    public static ScheduleStatusEnum toEnum(Integer code) {
        return map.get(code);
    }

    Integer code;
    String msg;
    ScheduleStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static Map<Integer, ScheduleStatusEnum> map = Maps.newHashMap();

    static {
        for (ScheduleStatusEnum item : ScheduleStatusEnum.values()) {
            map.put(item.getCode(), item);
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ScheduleStatusEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
