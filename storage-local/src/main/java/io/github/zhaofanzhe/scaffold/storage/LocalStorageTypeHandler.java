package io.github.zhaofanzhe.scaffold.storage;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(LocalStorage.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class LocalStorageTypeHandler implements TypeHandler<LocalStorage> {

    private final LocalStorageFactory localStorageFactory;

    public LocalStorageTypeHandler(LocalStorageFactory localStorageFactory) {
        this.localStorageFactory = localStorageFactory;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalStorage parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getStorageId());
    }

    @Override
    public LocalStorage getResult(ResultSet rs, String columnName) throws SQLException {
        return localStorageFactory.find(rs.getString(columnName));
    }

    @Override
    public LocalStorage getResult(ResultSet rs, int columnIndex) throws SQLException {
        return localStorageFactory.find(rs.getString(columnIndex));
    }

    @Override
    public LocalStorage getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return localStorageFactory.find(cs.getString(columnIndex));
    }

}
