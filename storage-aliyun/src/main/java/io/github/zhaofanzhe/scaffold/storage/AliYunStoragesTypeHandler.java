package io.github.zhaofanzhe.scaffold.storage;

import io.github.zhaofanzhe.scaffold.mybatis.AppJacksonConfig;
import io.github.zhaofanzhe.scaffold.mybatis.AppJacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;


@MappedTypes(AliYunStorages.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class AliYunStoragesTypeHandler extends AppJacksonTypeHandler {

    public AliYunStoragesTypeHandler(AppJacksonConfig config) {
        super(config, AliYunStorages.class);
    }

}
