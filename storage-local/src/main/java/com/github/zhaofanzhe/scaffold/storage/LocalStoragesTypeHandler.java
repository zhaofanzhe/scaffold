package com.github.zhaofanzhe.scaffold.storage;

import com.github.zhaofanzhe.scaffold.mybatis.AppJacksonConfig;
import com.github.zhaofanzhe.scaffold.mybatis.AppJacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(LocalStorages.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class LocalStoragesTypeHandler extends AppJacksonTypeHandler {

    public LocalStoragesTypeHandler(AppJacksonConfig config) {
        super(config, LocalStorages.class);
    }

}
