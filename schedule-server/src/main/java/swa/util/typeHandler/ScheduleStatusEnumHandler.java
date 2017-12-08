package swa.util.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.util.ScheduleStatusEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jinyan on 12/8/17 12:08 PM.
 */
public class ScheduleStatusEnumHandler extends BaseTypeHandler<ScheduleStatusEnum> {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleStatusEnumHandler.class);


    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ScheduleStatusEnum scheduleStatusEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, scheduleStatusEnum.getCode());
    }

    public ScheduleStatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return ScheduleStatusEnum.toEnum(resultSet.getInt(s));
    }

    public ScheduleStatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return ScheduleStatusEnum.toEnum(resultSet.getInt(i));
    }

    public ScheduleStatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return ScheduleStatusEnum.toEnum(callableStatement.getInt(i));
    }
}
