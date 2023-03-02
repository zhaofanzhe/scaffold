package io.github.zhaofanzhe.scaffold.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppJacksonConfig {

    private final List<AppMapperBuilderCustomizer> customizers;

    private ObjectMapper objectMapper;

    /**
     * 数据库序列化json解析
     */
    private Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> customizers.forEach(it -> it.customize(builder));
    }

    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        final Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer = jackson2ObjectMapperBuilderCustomizer();
        jackson2ObjectMapperBuilderCustomizer.customize(jackson2ObjectMapperBuilder);
        return jackson2ObjectMapperBuilder;
    }

    private ObjectMapper objectMapper() {
        return jackson2ObjectMapperBuilder().build();
    }

    public ObjectMapper getObjectMapper() {
        if (objectMapper != null) {
            return objectMapper;
        }
        synchronized (this) {
            if (objectMapper != null) {
                return objectMapper;
            }
            objectMapper = objectMapper();
            return objectMapper;
        }
    }

}
