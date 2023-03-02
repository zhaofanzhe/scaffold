package com.github.zhaofanzhe.scaffold.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.zhaofanzhe.scaffold.mybatis.json.typehandler.*;
import com.github.zhaofanzhe.scaffold.mybatis.json.typehandler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import({
        BooleanJsonArrayTypeHandler.class,
        ByteJsonArrayTypeHandler.class,
        CharacterJsonArrayTypeHandler.class,
        DoubleJsonArrayTypeHandler.class,
        FloatJsonArrayTypeHandler.class,
        IntegerJsonArrayTypeHandler.class,
        LongJsonArrayTypeHandler.class,
        ShortJsonArrayTypeHandler.class,
        StringJsonArrayTypeHandler.class,
})
@Configuration
public class MybatisAutoConfiguration {

    /**
     * mybatis-plus 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 开启返回map结果集的下划线转驼峰
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }

    /**
     * 字段插入和更新数据自动填充线
     */
    @Bean
    public MybatisPlusConfig mybatisPlusConfig() {
        return new MybatisPlusConfig();
    }

    @Bean
    public AppJacksonConfig appJacksonConfig(List<AppMapperBuilderCustomizer> customizers) {
        return new AppJacksonConfig(customizers);
    }

}
