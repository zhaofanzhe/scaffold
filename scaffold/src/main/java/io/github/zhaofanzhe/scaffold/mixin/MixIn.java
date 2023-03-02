package io.github.zhaofanzhe.scaffold.mixin;

import java.util.Map;

public interface MixIn<T> {

    public void preprocess(Iterable<T> iterable);

    public Map<String, Object> mixIn(T object);

}
