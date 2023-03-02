package io.github.zhaofanzhe.scaffold;

import io.github.zhaofanzhe.scaffold.advice.ExceptionAdvice;
import io.github.zhaofanzhe.scaffold.config.AppConfig;
import io.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
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
