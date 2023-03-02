package io.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 分步混入
 * 每次混入,保留上一步结果
 */
public class StepMixIn<T> implements MixIn<T> {

    @SafeVarargs
    public static <T> MixIn<T> of(MixIn<T>... mixIns) {
        return new StepMixIn<>(mixIns);
    }

    private final MixIn<T>[] mixIns;

    private final Map<T, Map<String, Object>> caches = new HashMap<>();

    @SafeVarargs
    public StepMixIn(MixIn<T>... mixIns) {
        this.mixIns = mixIns;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void preprocess(Iterable<T> iterable) {
        iterable.forEach(object -> caches.put(object, BeanUtil.beanToMap(object)));
        final Iterable<Map<String, Object>> values = caches.values().stream().toList();
        for (MixIn<T> mixIn : this.mixIns) {
            mixIn.preprocess((Iterable<T>) values);
            for (Map<String, Object> object : values) {
                MixIns.copyProperties(mixIn.mixIn((T) object), object);
            }
        }
    }

    @Override
    public Map<String, Object> mixIn(T object) {
        return caches.get(object);
    }

}
