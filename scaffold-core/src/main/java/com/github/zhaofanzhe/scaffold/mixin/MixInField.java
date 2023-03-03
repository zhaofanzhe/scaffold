package com.github.zhaofanzhe.scaffold.mixin;

import cn.hutool.core.bean.BeanUtil;

public class MixInField {

    public static MixInField of(String... names) {
        if (names.length == 0) {
            throw new RuntimeException("names 的长度最小为 1");
        }
        return new MixInField(names);
    }

    private final String[] names;

    private MixInField(String[] names) {
        this.names = names;
    }

    @SuppressWarnings("unchecked")
    public <T, ID> ID getId(T object) {
        Object value = object;
        for (String name : names) {
            if (value == null) {
                return null;
            }
            value = BeanUtil.getFieldValue(value, name);
        }
        return (ID) value;
    }

}
