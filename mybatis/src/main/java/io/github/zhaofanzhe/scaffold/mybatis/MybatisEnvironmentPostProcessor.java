package io.github.zhaofanzhe.scaffold.mybatis;

import io.github.zhaofanzhe.scaffold.autoconfig.YamlEnvironmentPostProcessor;

public class MybatisEnvironmentPostProcessor extends YamlEnvironmentPostProcessor {

    public MybatisEnvironmentPostProcessor() {
        super("application-mybatis.yaml");
    }

}
