package a.b.c.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.lang.reflect.InvocationHandler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
public class THandler extends BaseTypeHandler<Integer> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Integer aLong, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, aLong);
    }

    @Override
    public Integer getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getInt(s) * 100;
    }

    @Override
    public Integer getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt(i) * 100;
    }

    @Override
    public Integer getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getInt(i) * 100;
    }
}
