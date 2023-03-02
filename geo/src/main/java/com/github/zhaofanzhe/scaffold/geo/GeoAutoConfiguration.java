package com.github.zhaofanzhe.scaffold.geo;

import com.github.zhaofanzhe.scaffold.mybatis.AppJacksonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        // GEO
        MySQLGeoPointTypeHandler.class,
        MySQLGeoPolygonTypeHandler.class,
})
@Configuration
public class GeoAutoConfiguration {

    @Bean
    public MySQLGeoPointTypeHandler mySQLGeoPointTypeHandler() {
        return new MySQLGeoPointTypeHandler();
    }

    @Bean
    public MySQLGeoPolygonTypeHandler mySQLGeoPolygonTypeHandler(AppJacksonConfig config) {
        return new MySQLGeoPolygonTypeHandler(config);
    }

}
