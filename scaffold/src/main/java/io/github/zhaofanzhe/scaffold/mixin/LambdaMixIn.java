package io.github.zhaofanzhe.scaffold.mixin;

import java.util.Map;
import java.util.function.Function;

/**
 * lambda混入
 */
public class LambdaMixIn<T> implements MixIn<T> {

    public static <T> MixIn<T> of(String key, Function<Object, Object> mixInFunction) {
        return new LambdaMixIn<>(object -> Map.of(key, mixInFunction.apply(object)));
    }

    public static <T> MixIn<T> of(Function<Object, Map<String, Object>> mixInFunction) {
        return new LambdaMixIn<>(mixInFunction);
    }

    private final Function<Object, Map<String, Object>> mixInFunction;

    public LambdaMixIn(Function<Object, Map<String, Object>> mixInFunction) {
        this.mixInFunction = mixInFunction;
    }

    @Override
    public void preprocess(Iterable<T> iterable) {

    }

    @Override
    public Map<String, Object> mixIn(Object object) {
        if (mixInFunction == null) {
            return Map.of();
        }
        return this.mixInFunction.apply(object);
    }

}
