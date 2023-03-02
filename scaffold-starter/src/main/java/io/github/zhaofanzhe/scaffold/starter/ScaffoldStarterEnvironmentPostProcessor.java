package io.github.zhaofanzhe.scaffold.starter;

import io.github.zhaofanzhe.scaffold.autoconfig.YamlEnvironmentPostProcessor;

public class ScaffoldStarterEnvironmentPostProcessor extends YamlEnvironmentPostProcessor {

    public ScaffoldStarterEnvironmentPostProcessor(){
        super("application-starter.yaml");
    }

}
