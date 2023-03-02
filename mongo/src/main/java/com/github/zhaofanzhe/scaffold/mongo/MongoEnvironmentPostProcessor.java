package com.github.zhaofanzhe.scaffold.mongo;

import com.github.zhaofanzhe.scaffold.autoconfig.YamlEnvironmentPostProcessor;

public class MongoEnvironmentPostProcessor extends YamlEnvironmentPostProcessor {

    public MongoEnvironmentPostProcessor() {
        super("application-mongo.yaml");
    }

}
