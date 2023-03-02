package io.github.zhaofanzhe.scaffold.geo;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.math.BigDecimal;
import java.util.List;

public class MongoGeoUtil {

    /**
     * GeoPolygon 转换为 GeoJsonPolygon
     */
    public static GeoJsonPoint toGeoJsonPoint(GeoPoint point) {
        return new GeoJsonPoint(point.getLng().doubleValue(), point.getLat().doubleValue());
    }

    /**
     * GeoPolygon 转换为 GeoJsonPolygon
     */
    public static GeoPoint toGeoPoint(GeoJsonPoint point) {
        return new GeoPoint(BigDecimal.valueOf(point.getX()), BigDecimal.valueOf(point.getY()));
    }

    /**
     * GeoPolygon 转换为 GeoJsonPolygon
     */
    public static GeoJsonPolygon toGeoJsonPolygon(GeoPolygon polygon) {
        // geo json 坐标
        // lng(经度)在前,lat(纬度)在后
        final List<Point> points = polygon.stream()
                .map(point -> new Point(point.getLng().doubleValue(), point.getLat().doubleValue()))
                .toList();
        // geo json 多边形
        return new GeoJsonPolygon(points);
    }

    /**
     * GeoPolygon 转换为 GeoJsonPolygon
     */
    public static GeoPolygon toGeoPolygon(GeoJsonPolygon polygon) {
        final GeoPolygon geoPolygon = new GeoPolygon();
        polygon.getCoordinates().get(0).getCoordinates();
        for (Point point : polygon.getCoordinates().get(0).getCoordinates()) {
            geoPolygon.add(new GeoPoint(BigDecimal.valueOf(point.getX()), BigDecimal.valueOf(point.getY())));
        }
        return geoPolygon;
    }

}
