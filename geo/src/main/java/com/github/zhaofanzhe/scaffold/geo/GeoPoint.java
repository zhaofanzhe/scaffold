package com.github.zhaofanzhe.scaffold.geo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 坐标类
 */
public class GeoPoint {

    /**
     * 经度
     */
    @NotNull
    private BigDecimal lng;

    /**
     * 纬度
     */
    @NotNull
    private BigDecimal lat;

    public GeoPoint() {
    }

    public GeoPoint(BigDecimal lng, BigDecimal lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
               "lng=" + lng +
               ", lat=" + lat +
               '}';
    }
}
