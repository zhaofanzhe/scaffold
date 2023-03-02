package com.github.zhaofanzhe.scaffold.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    /**
     * 数据源配置的前缀，必须与application.properties中配置的对应数据源的前缀一致
     */
    private static final String BUSINESS_DATASOURCE_PREFIX = "spring.datasource.primary";

    private static final String QUARTZ_DATASOURCE_PREFIX = "spring.datasource.quartz";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = BUSINESS_DATASOURCE_PREFIX)
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = QUARTZ_DATASOURCE_PREFIX)
    public DataSourceProperties quartzDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties()
                .initializeDataSourceBuilder().build();
    }

    /**
     * 配置Quartz独立数据源的配置
     */
    @Bean
    @QuartzDataSource
    public DataSource quartzDataSource() {
        return quartzDataSourceProperties()
                .initializeDataSourceBuilder().build();
    }

}
