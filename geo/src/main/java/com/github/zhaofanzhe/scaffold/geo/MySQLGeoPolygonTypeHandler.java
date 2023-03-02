package com.github.zhaofanzhe.scaffold.geo;

import com.github.zhaofanzhe.scaffold.mybatis.AppJacksonConfig;
import com.github.zhaofanzhe.scaffold.mybatis.AppJacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(GeoPolygon.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MySQLGeoPolygonTypeHandler extends AppJacksonTypeHandler {

    public MySQLGeoPolygonTypeHandler(AppJacksonConfig config) {
        super(config, GeoPolygon.class);
    }

}
