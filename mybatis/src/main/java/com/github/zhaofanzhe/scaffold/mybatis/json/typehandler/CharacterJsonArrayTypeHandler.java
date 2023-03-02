package com.github.zhaofanzhe.scaffold.mybatis.json.typehandler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.github.zhaofanzhe.scaffold.mybatis.json.CharacterJsonArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@Slf4j
@MappedTypes({CharacterJsonArray.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class CharacterJsonArrayTypeHandler extends JacksonTypeHandler {

    public CharacterJsonArrayTypeHandler() {
        super(CharacterJsonArray.class);
    }

}
