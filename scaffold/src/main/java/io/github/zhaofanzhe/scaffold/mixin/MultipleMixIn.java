package io.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * 多混入
 */
public class MultipleMixIn<T> implements MixIn<T> {

    @SafeVarargs
    public static <T> MixIn<T> of(MixIn<T>... mixIns) {
        return new MultipleMixIn<>(mixIns);
    }

    @SafeVarargs
    public static <T> Map<String, Object> convert(T object, MixIn<T>... mixIns) {
        final MultipleMixIn<T> mixIn = new MultipleMixIn<>(mixIns);
        mixIn.preprocess(List.of(object));
        return mixIn.apply(object);
    }

    @SafeVarargs
    public static <T> List<Map<String, Object>> converts(Iterable<T> iterable, MixIn<T>... mixIns) {
        final MultipleMixIn<T> mixIn = new MultipleMixIn<>(mixIns);
        mixIn.preprocess(iterable);
        return StreamSupport.stream(iterable.spliterator(), false).map(mixIn::apply).toList();
    }

    private final MixIn<T>[] mixIns;

    @SafeVarargs
    public MultipleMixIn(MixIn<T>... mixIns) {
        this.mixIns = mixIns;
    }

    @Override
    public void preprocess(Iterable<T> iterable) {
        for (MixIn<T> mixIn : this.mixIns) {
            mixIn.preprocess(iterable);
        }
    }

    @Override
    public Map<String, Object> mixIn(T object) {
        final Map<String, Object> result = new HashMap<>();
        for (MixIn<T> mixIn : mixIns) {
            final Map<String, Object> m = mixIn.mixIn(object);
            for (String key : m.keySet()) {
                result.put(key, m.get(key));
            }
        }
        return result;
    }


    public Map<String, Object> apply(T object) {
        final Map<String, Object> result = BeanUtil.beanToMap(object);
        final Map<String, Object> map = mixIn(object);
        MixIns.copyProperties(map, result);
        return result;
    }

}
