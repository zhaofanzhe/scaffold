package io.github.zhaofanzhe.scaffold.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Slf4j
public class YamlEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    private final String[] files;

    public YamlEnvironmentPostProcessor(String... files) {
        this.files = files;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        for (String file : files) {
            postProcessEnvironment(environment, application, file);
        }
    }

    private void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application, String file) {
        Resource path = new ClassPathResource(file);
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exists");
        }
        try {
            List<PropertySource<?>> load = loader.load(file, path);
            for (PropertySource<?> propertySource : load) {
                environment.getPropertySources().addLast(propertySource);
            }
            log.info("已加载 {} 配置文件", file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to load yaml configuration from " + path, e);
        }
    }

}
