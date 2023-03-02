package io.github.zhaofanzhe.scaffold.quartz;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 为 quartz 配置事务管理器
     */
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer() {
        return schedulerFactoryBean -> schedulerFactoryBean.setTransactionManager(platformTransactionManager);
    }

}
