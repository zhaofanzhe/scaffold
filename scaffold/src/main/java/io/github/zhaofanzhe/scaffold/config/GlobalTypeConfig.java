package io.github.zhaofanzhe.scaffold.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 全局类型转换配置
 * 处理json和其它参数里的时间
 * Storage配置
 */
@Configuration
public class GlobalTypeConfig {

    /**
     * DateTime格式化字符串
     */
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date格式化字符串
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Time格式化字符串
     */
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * 年和月格式
     */
    private static final String DEFAULT_YEAR_MONTH_FORMAT = "yyyy-MM";

    /**
     * 日期正则表达式
     */
    private static final String DATE_REGEX = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    /**
     * 时间正则表达式
     */
    private static final String TIME_REGEX = "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";

    /**
     * 日期和时间正则表达式
     */
    private static final String DATE_TIME_REGEX = DATE_REGEX + "\\s" + TIME_REGEX;

    /**
     * 年和月正则表达式
     */
    private static final String YEAR_MONTH_REGEX = "[1-9]\\d{3}-(0[1-9]|1[0-2])";

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        // 不要转换成lambda,lambda不包含泛型信息会报错
        // noinspection Convert2Lambda
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (StrUtil.isEmpty(source)) {
                    return null;
                }
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
            }
        };
    }

    /**
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        // 不要转换成lambda,lambda不包含泛型信息会报错
        // noinspection Convert2Lambda
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (StrUtil.isEmpty(source)) {
                    return null;
                }
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
            }
        };
    }

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        // 不要转换成lambda,lambda不包含泛型信息会报错
        // noinspection Convert2Lambda
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                if (StrUtil.isEmpty(source)) {
                    return null;
                }
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN));
            }
        };
    }

    /**
     * Date转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        // 不要转换成lambda,lambda不包含泛型信息会报错
        // noinspection Convert2Lambda
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (StrUtil.isEmpty(source)) {
                    return null;
                }
                DateFormat format;
                if (source.matches(DATE_TIME_REGEX)) {
                    format = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
                } else if (source.matches(DATE_REGEX)) {
                    format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                } else if (source.matches(YEAR_MONTH_REGEX)) {
                    format = new SimpleDateFormat(DEFAULT_YEAR_MONTH_FORMAT);
                } else {
                    throw new IllegalArgumentException();
                }
                try {
                    return format.parse(source);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)))
                .serializerByType(Date.class, new DateSerializer(false, new SimpleDateFormat(DEFAULT_DATETIME_FORMAT)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)))
                .deserializerByType(Date.class, new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance, new SimpleDateFormat(DEFAULT_DATETIME_FORMAT), DEFAULT_DATETIME_FORMAT));
    }

}
