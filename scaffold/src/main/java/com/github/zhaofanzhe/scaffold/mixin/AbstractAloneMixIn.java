package com.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.map.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象单独混入
 */
public abstract class AbstractAloneMixIn<T, ID> implements MixIn<T> {

    private final Func1<T, ID> getId;

    private final Boolean isMixIn;

    private final Map<ID, Map<String, Object>> caches = new HashMap<>();

    public AbstractAloneMixIn(Func1<T, ID> getId, Boolean isMixIn) {
        this.getId = getId;
        this.isMixIn = isMixIn;
    }

    public AbstractAloneMixIn(MixInField field, Boolean isMixIn) {
        this(field::getId, isMixIn);
    }

    public AbstractAloneMixIn(ID id, Boolean isMixIn) {
        this((object) -> id, isMixIn);
    }

    public boolean isMixIn() {
        return isMixIn != null && isMixIn;
    }

    @Override
    public void preprocess(Iterable<T> iterable) {
    }

    @Override
    public Map<String, Object> mixIn(T object) {
        if (!this.isMixIn()) {
            return MapUtil.empty();
        }

        if (object == null) {
            return MapUtil.empty();
        }

        final ID id = getId.callWithRuntimeException(object);

        if (id == null) {
            return MapUtil.empty();
        }

        return getByCache(id);
    }

    private Map<String, Object> getByCache(ID id) {
        final Map<String, Object> cache = caches.get(id);

        if (cache != null) {
            return cache;
        }

        final Map<String, Object> result = this.onMixIn(id);

        caches.put(id, result);

        return result;
    }

    public abstract Map<String, Object> onMixIn(ID id);

}
