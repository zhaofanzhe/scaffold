package io.github.zhaofanzhe.scaffold.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("storage.local")
public class LocalStorageConfig {

    /**
     * 本地路径
     */
    private String path;

    /**
     * 基础url
     */
    private String baseUrl;

}
