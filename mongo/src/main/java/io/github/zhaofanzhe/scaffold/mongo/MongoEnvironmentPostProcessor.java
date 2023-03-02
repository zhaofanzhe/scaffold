package io.github.zhaofanzhe.scaffold.mongo;

import io.github.zhaofanzhe.scaffold.autoconfig.YamlEnvironmentPostProcessor;

public class MongoEnvironmentPostProcessor extends YamlEnvironmentPostProcessor {

    public MongoEnvironmentPostProcessor() {
        super("application-mongo.yaml");
    }

}
