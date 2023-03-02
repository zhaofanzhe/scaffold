package com.github.zhaofanzhe.scaffold.starter;

import com.github.zhaofanzhe.scaffold.autoconfig.YamlEnvironmentPostProcessor;

public class ScaffoldStarterEnvironmentPostProcessor extends YamlEnvironmentPostProcessor {

    public ScaffoldStarterEnvironmentPostProcessor(){
        super("application-starter.yaml");
    }

}
