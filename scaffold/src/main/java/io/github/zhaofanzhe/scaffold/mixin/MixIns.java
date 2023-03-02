package io.github.zhaofanzhe.scaffold.mixin;

import java.util.Map;

public class MixIns {

    /**
     * 删除标记
     */
    public static final Object DELETE_FLAG = new Object();

    public static void copyProperties(Map<String, Object> source, Map<String, Object> target) {
        for (String key : source.keySet()) {
            final Object value = source.get(key);
            if (value == MixIns.DELETE_FLAG) {
                target.remove(key);
            } else {
                target.put(key, value);
            }
        }
    }

}
