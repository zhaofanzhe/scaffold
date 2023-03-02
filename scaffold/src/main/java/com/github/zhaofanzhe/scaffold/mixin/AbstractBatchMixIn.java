package com.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.map.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

/**
 * 抽象批量混入
 */
public abstract class AbstractBatchMixIn<T, R, ID> implements MixIn<T> {

    private final Func1<T, ID> getId;

    private final Boolean isMixIn;

    private List<ID> ids;

    private Map<ID, R> caches;

    public AbstractBatchMixIn(Func1<T, ID> getId, Boolean isMixIn) {
        this.getId = getId;
        this.isMixIn = isMixIn;
    }

    public AbstractBatchMixIn(MixInField field, Boolean isMixIn) {
        this(field::getId, isMixIn);
    }

    public AbstractBatchMixIn(ID id, Boolean isMixIn) {
        this((object) -> id, isMixIn);
    }

    public List<ID> getIds() {
        return ids;
    }

    public abstract Map<ID, R> onBatch(List<ID> ids);

    public void preprocess(Iterable<T> iterable) {

        if (this.isMixIn == null || !this.isMixIn) {
            this.caches = Map.of();
            return;
        }

        this.ids = StreamSupport.stream(iterable.spliterator(), false)
                .filter(Objects::nonNull)
                .map(getId::callWithRuntimeException)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (this.ids.isEmpty()) {
            this.caches = Map.of();
            return;
        }

        this.caches = this.onBatch(ids);
    }

    @Override
    public Map<String, Object> mixIn(T object) {
        if (this.isMixIn == null || !this.isMixIn) {
            return MapUtil.empty();
        }
        final ID id = getId.callWithRuntimeException(object);
        if (id == null) {
            return MapUtil.empty();
        }
        if (!this.caches.containsKey(id)) {
            return MapUtil.empty();
        }
        return this.onMixIn(caches.get(id));
    }

    public abstract Map<String, Object> onMixIn(R object);

    public Map<ID, R> toMap(List<R> list, Func1<R, ID> getId) {
        final HashMap<ID, R> map = new HashMap<>();
        list.forEach(object -> map.put(getId.callWithRuntimeException(object), object));
        return map;
    }

    public Map<ID, R> toMap(List<R> list, String idName) {
        //noinspection unchecked
        return toMap(list, (Func1<R, ID>) parameter -> (ID) BeanUtil.getFieldValue(parameter, idName));
    }

    public Map<ID, R> toMap(List<R> list, Func1<R, ID> getId, Supplier<R> getDefault) {
        final Map<ID, R> map = toMap(list, getId);
        this.getIds().forEach(id -> {
            if (!map.containsKey(id)) {
                map.put(id, getDefault.get());
            }
        });
        return map;
    }

    public Map<ID, R> toMap(List<R> list, String idName, Supplier<R> getDefault) {
        //noinspection unchecked
        return toMap(list, (Func1<R, ID>) parameter -> (ID) BeanUtil.getFieldValue(parameter, idName), getDefault);
    }

}
