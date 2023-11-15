package com.github.zhaofanzhe.scaffold.mixin;

import java.util.Arrays;
import java.util.Map;

/**
 * 删除混入
 * 将指定字段标记为删除
 */
public class DeleteMixIn<T> implements MixIn<T> {

    public static <T> MixIn<T> of(String... fields) {
        return new DeleteMixIn<>(fields);
    }

    private final String[] fields;

    public DeleteMixIn(String... fields) {
        this.fields = fields;
    }

    @Override
    public void preprocess(Iterable<T> iterable) {

    }

    @Override
    public Map<String, Object> mixIn(T object) {
        //noinspection unchecked
        return Map.ofEntries(
                Arrays.stream(fields)
                        .map(key -> Map.entry(key, MixIns.DELETE_FLAG))
                        .toArray(Map.Entry[]::new)
        );
    }

}
