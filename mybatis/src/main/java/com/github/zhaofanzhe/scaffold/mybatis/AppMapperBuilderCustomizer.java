package com.github.zhaofanzhe.scaffold.mybatis;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public interface AppMapperBuilderCustomizer {

    void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder);

}
