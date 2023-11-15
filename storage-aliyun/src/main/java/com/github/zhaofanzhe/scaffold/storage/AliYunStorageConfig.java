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

    public static enum Protocol {
        HTTP,
        HTTPS
    }

    /**
     * AccessKeyId
     */
    private String accessKeyId;

    /**
     * SecretAccessKey
     */
    private String secretAccessKey;

    /**
     * 协议
     */
    private Protocol protocol = Protocol.HTTP;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * 存储桶
     */
    private String bucket;

}
