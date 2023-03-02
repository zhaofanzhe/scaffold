package com.github.zhaofanzhe.scaffold.geo;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(GeoPoint.class)
@MappedJdbcTypes(JdbcType.BLOB)
public class MySQLGeoPointTypeHandler implements TypeHandler<GeoPoint> {

    private final GeometryFactory geometryFactory;

    public MySQLGeoPointTypeHandler() {
        this(0);
    }

    public MySQLGeoPointTypeHandler(int srid) {
        geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, GeoPoint geoPoint, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBytes(i, toMysqlWkb(geoPoint));
    }

    @Override
    public GeoPoint getResult(ResultSet rs, String s) throws SQLException {
        return fromMysqlWkb(rs.getBytes(s));
    }

    @Override
    public GeoPoint getResult(ResultSet rs, int i) throws SQLException {
        return fromMysqlWkb(rs.getBytes(i));
    }

    @Override
    public GeoPoint getResult(CallableStatement cs, int i) throws SQLException {
        return fromMysqlWkb(cs.getBytes(i));
    }

    private GeoPoint fromMysqlWkb(byte[] bytes) {

        if (bytes == null) {
            return null;
        }

        try {

            WKBReader wkbReader = new WKBReader(geometryFactory);

            byte[] geomBytes = ByteBuffer.allocate(bytes.length - 4)
                    .order(ByteOrder.LITTLE_ENDIAN)
                    .put(bytes, 4, bytes.length - 4)
                    .array();

            Geometry geometry = wkbReader.read(geomBytes);

            Point point = (Point) geometry;

            return new GeoPoint(BigDecimal.valueOf(point.getX()), BigDecimal.valueOf(point.getY()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] toMysqlWkb(GeoPoint point) {
        final CoordinateSequenceFactory coordinateSequenceFactory = geometryFactory.getCoordinateSequenceFactory();

        final CoordinateSequence sequence = coordinateSequenceFactory.create(1, 0);

        sequence.setOrdinate(0, 0, point.getLng().doubleValue());
        sequence.setOrdinate(0, 1, point.getLat().doubleValue());

        final Point geometry = geometryFactory.createPoint(sequence);

        WKBWriter wkbWriter = new WKBWriter(2, 2);

        final byte[] bytes = wkbWriter.write(geometry);

        return ByteBuffer.allocate(bytes.length + 4)
                .put(new byte[]{0, 0, 0, 0})
                .put(bytes, 0, bytes.length).array();
    }

}
