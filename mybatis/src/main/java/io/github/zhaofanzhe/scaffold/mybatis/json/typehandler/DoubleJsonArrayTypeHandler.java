package io.github.zhaofanzhe.scaffold.mybatis.json.typehandler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.zhaofanzhe.scaffold.mybatis.json.DoubleJsonArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@Slf4j
@MappedTypes({DoubleJsonArray.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class DoubleJsonArrayTypeHandler extends JacksonTypeHandler {

    public DoubleJsonArrayTypeHandler() {
        super(DoubleJsonArray.class);
    }

}