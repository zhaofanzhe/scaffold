package com.github.zhaofanzhe.scaffold.mybatis;

import com.github.zhaofanzhe.scaffold.autoconfig.YamlEnvironmentPostProcessor;

public class MybatisEnvironmentPostProcessor extends YamlEnvironmentPostProcessor {

    public MybatisEnvironmentPostProcessor() {
        super("application-mybatis.yaml");
    }

}
