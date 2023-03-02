package io.github.zhaofanzhe.scaffold.wx;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WxConfig.class})
public class WxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WxClientHub.class)
    public WxClientHub wxClientHub(WxConfig wxConfig) {
        return new WxClientHub(wxConfig);
    }

}
