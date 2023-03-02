package com.github.zhaofanzhe.scaffold.storage;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AliYunStorage.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class AliYunStorageTypeHandler implements TypeHandler<AliYunStorage> {

    private final AliYunStorageFactory aliYunStorageFactory;

    public AliYunStorageTypeHandler(AliYunStorageFactory aliYunStorageFactory) {
        this.aliYunStorageFactory = aliYunStorageFactory;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, AliYunStorage parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getStorageId());
    }

    @Override
    public AliYunStorage getResult(ResultSet rs, String columnName) throws SQLException {
        return aliYunStorageFactory.find(rs.getString(columnName));
    }

    @Override
    public AliYunStorage getResult(ResultSet rs, int columnIndex) throws SQLException {
        return aliYunStorageFactory.find(rs.getString(columnIndex));
    }

    @Override
    public AliYunStorage getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return aliYunStorageFactory.find(cs.getString(columnIndex));
    }

}
