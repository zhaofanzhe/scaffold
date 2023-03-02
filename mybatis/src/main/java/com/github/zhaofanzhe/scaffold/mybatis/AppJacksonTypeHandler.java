package com.github.zhaofanzhe.scaffold.mybatis;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;

@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public abstract class AppJacksonTypeHandler extends AbstractJsonTypeHandler<Object> {

    private final ObjectMapper objectMapper;

    private final Class<?> type;

    public AppJacksonTypeHandler(AppJacksonConfig config, Class<?> type) {
        this.objectMapper = config.getObjectMapper();
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }

    protected Object parse(String json) {
        try {
            return getObjectMapper().readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String toJson(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

}
