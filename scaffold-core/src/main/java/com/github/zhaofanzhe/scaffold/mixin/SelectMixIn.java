package com.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择混入
 * 只展示被选中的字段, 将没有被选中的字段标记为删除
 */
public class SelectMixIn<T> implements MixIn<T> {

    public static class SelectOption {

        protected final Map<String[], String> mapping = new HashMap<>();

        public SelectOption select(String key) {
            select(key, key);
            return this;
        }

        public SelectOption select(String oldKey, String newKey) {
            select(new String[]{oldKey}, newKey);
            return this;
        }

        public SelectOption select(String[] oldKeys, String newKey) {
            this.mapping.put(oldKeys, newKey);
            return this;
        }

    }

    public static SelectOption select(String key) {
        return select(key, key);
    }

    public static SelectOption select(String oldKey, String newKey) {
        return select(new String[]{oldKey}, newKey);
    }

    public static SelectOption select(String[] oldKeys, String newKey) {
        final SelectOption option = new SelectOption();
        option.mapping.put(oldKeys, newKey);
        return option;
    }

    @SafeVarargs
    public static <T> SelectMixIn<T> of(SelectOption option, MixIn<T>... mixIns) {
        return new SelectMixIn<>(option, mixIns);
    }

    private final MixIn<T> mixIn;

    private final Map<String[], String> mapping;

    @SafeVarargs
    public SelectMixIn(SelectOption option, MixIn<T>... mixIns) {
        mapping = option.mapping;
        mixIn = new MultipleMixIn<>(mixIns);
    }

    @Override
    public void preprocess(Iterable<T> iterable) {
        mixIn.preprocess(iterable);
    }

    @Override
    public Map<String, Object> mixIn(T object) {
        if (object == null) {
            return Map.of();
        }
        final Map<String, Object> source = BeanUtil.beanToMap(object);
        MixIns.copyProperties(mixIn.mixIn(object), source);
        final HashMap<String, Object> result = new HashMap<>();
        final List<String> fields = source.keySet().stream().toList();
        for (String field : fields) {
            result.put(field, MixIns.DELETE_FLAG);
        }
        for (String[] oldKeys : this.mapping.keySet()) {
            final String newKey = this.mapping.get(oldKeys);
            final Object value = getValue(source, oldKeys);
            result.put(newKey, value);
        }
        return result;
    }

    private Object getValue(Object object, String[] keys) {
        Object value = object;
        for (String key : keys) {
            if (value == null) {
                return null;
            }
            value = BeanUtil.getFieldValue(value, key);
        }
        return value;
    }

}
