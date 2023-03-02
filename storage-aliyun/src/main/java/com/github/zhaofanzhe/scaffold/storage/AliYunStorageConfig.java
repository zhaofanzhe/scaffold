package com.github.zhaofanzhe.scaffold.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云存储配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "storage.aliyun")
public class AliYunStorageConfig {

    /**
     * 资源key
     */
    private String accessKeyId;

    /**
     * 安全key
     */
    private String accessKeySecret;

    /**
     * 端口
     */
    private String endpoint;

    /**
     * 存储桶
     */
    private String bucket;

}
