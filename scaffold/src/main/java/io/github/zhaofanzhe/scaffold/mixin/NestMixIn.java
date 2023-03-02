package io.github.zhaofanzhe.scaffold.mixin;

import io.github.zhaofanzhe.scaffold.tuples.Pair;
import io.github.zhaofanzhe.scaffold.tuples.Tuples;

import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 嵌套混入
 */
public class NestMixIn<T> implements MixIn<T> {

    @SafeVarargs
    public static <T> NestMixIn<T> of(MixIn<T> mixIn, MixIn<T>... mixIns) {
        return of(mixIn, null, mixIns);
    }

    @SafeVarargs
    public static <T> NestMixIn<T> of(MixIn<T> mixIn, String field, MixIn<T>... mixIns) {
        return new NestMixIn<>(mixIn, field, mixIns);
    }

    private final MixIn<T> mixIn;

    private final String field;

    private final MixIn<?>[] mixIns;

    private NestMixIn(MixIn<T> mixIn, String field, MixIn<?>[] mixIns) {
        this.mixIn = mixIn;
        this.field = field;
        this.mixIns = mixIns;
    }

    @Override
    public void preprocess(Iterable<T> iterable) {
        this.mixIn.preprocess(iterable);
    }

    @Override
    public Map<String, Object> mixIn(T object) {
        final Map<String, Object> m = this.mixIn.mixIn(object);
        final Pair<String, Object> pair = getUniquePair(m);
        final String key = pair.getValue0();
        Object value = pair.getValue1();
        if (value instanceof List<?> list) {
            //noinspection unchecked
            value = MultipleMixIn.converts((List<Object>) list, (MixIn<Object>[]) this.mixIns);
        } else {
            //noinspection unchecked
            value = MultipleMixIn.convert(value, (MixIn<Object>[]) this.mixIns);
        }
        m.put(key, value);
        return m;
    }

    public Pair<String, Object> getUniquePair(Map<String, Object> m) {

        if (this.field != null) {
            return Tuples.of(field, MixInField.of(field).getId(m));
        }

        // 过滤不符合条件的元素
        final List<Map.Entry<String, Object>> list = m.entrySet().stream()
                .filter(entry -> isNotBaseType(entry.getValue())).toList();

        if (list.size() == 1) {
            final Map.Entry<String, Object> entry = list.get(0);
            return Tuples.of(entry.getKey(), entry.getValue());
        }

        throw new RuntimeException("推导 UniquePair 失败，请添加 filed 参数进行提示");
    }

    private boolean isBaseType(Object object) {
        return object instanceof Byte ||
               object instanceof Short ||
               object instanceof Integer ||
               object instanceof Long ||
               object instanceof Float ||
               object instanceof Double ||
               object instanceof Boolean ||
               object instanceof Character ||
               object instanceof String ||
               object instanceof Date ||
               object instanceof Temporal;
    }

    private boolean isNotBaseType(Object object) {
        return !isBaseType(object);
    }

}
