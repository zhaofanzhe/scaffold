package com.github.zhaofanzhe.scaffold;

import com.github.zhaofanzhe.scaffold.advice.ExceptionAdvice;
import com.github.zhaofanzhe.scaffold.config.AppConfig;
import com.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        ExceptionAdvice.class,
        SpringContextHolder.class,
})
@EnableConfigurationProperties({AppConfig.class})
@Configuration
public class ScaffoldAutoConfiguration {

}
