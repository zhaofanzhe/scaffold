package com.github.zhaofanzhe.scaffold.storage.secure;

import cn.hutool.core.net.url.UrlQuery;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public abstract class Attributes {

    private final HashMap<String, String> attributes = new HashMap<>();

    public Set<String> keySet() {
        return attributes.keySet();
    }

    protected void reset() {
        attributes.clear();
    }

    public void set(String key, String value) {
        attributes.put(key, value);
    }

    public String get(String key) {
        return attributes.get(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        final String value = get(key);
        return value == null ? defaultValue : value;
    }

    protected String build(String... excludes) {
        UrlQuery query = new UrlQuery();
        attributes.keySet().stream().sorted().forEach(key -> {
            if (Arrays.asList(excludes).contains(key)) {
                return;
            }
            query.add(key, attributes.get(key));
        });
        return query.build(StandardCharsets.UTF_8);
    }

}
