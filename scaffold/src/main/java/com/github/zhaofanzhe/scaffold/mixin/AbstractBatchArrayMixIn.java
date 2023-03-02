package com.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.func.Func1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 抽象批量数组混入
 */
public abstract class AbstractBatchArrayMixIn<T, R, ID> extends AbstractBatchMixIn<T, List<R>, ID> {

    public AbstractBatchArrayMixIn(Func1<T, ID> getId, Boolean isMixIn) {
        super(getId, isMixIn);
    }

    public AbstractBatchArrayMixIn(MixInField field, Boolean isMixIn) {
        super(field, isMixIn);
    }

    public AbstractBatchArrayMixIn(ID id, Boolean isMixIn) {
        super(id, isMixIn);
    }

    @Override
    public abstract Map<ID, List<R>> onBatch(List<ID> ids);

    @Override
    public abstract Map<String, Object> onMixIn(List<R> object);

    public Map<ID, List<R>> toMapArray(List<R> list, Func1<R, ID> getId) {
        final Map<ID, List<R>> map = new HashMap<>();
        list.forEach(object -> map.computeIfAbsent(getId.callWithRuntimeException(object), k -> new ArrayList<>()).add(object));
        return map;
    }

    public Map<ID, List<R>> toMapArray(List<R> list, String idName) {
        //noinspection unchecked
        return toMapArray(list, (Func1<R, ID>) parameter -> (ID) BeanUtil.getFieldValue(parameter, idName));
    }

    public Map<ID, List<R>> toMapArray(List<R> list, Func1<R, ID> getId, Supplier<List<R>> getDefault) {
        final Map<ID, List<R>> map = this.toMapArray(list, getId);
        for (ID id : getIds()) {
            if (!map.containsKey(id)) {
                map.put(id, getDefault.get());
            }
        }
        return map;
    }

    public Map<ID, List<R>> toMapArray(List<R> list, String idName, Supplier<List<R>> getDefault) {
        //noinspection unchecked
        return toMapArray(list, (Func1<R, ID>) parameter -> (ID) BeanUtil.getFieldValue(parameter, idName), getDefault);
    }

    public Map<ID, List<R>> toMapArrayOrEmpty(List<R> list, Func1<R, ID> getId) {
        return this.toMapArray(list, getId, List::of);
    }

    public Map<ID, List<R>> toMapArrayOrEmpty(List<R> list, String idName) {
        //noinspection unchecked
        return toMapArray(list, (Func1<R, ID>) parameter -> (ID) BeanUtil.getFieldValue(parameter, idName), List::of);
    }

}
