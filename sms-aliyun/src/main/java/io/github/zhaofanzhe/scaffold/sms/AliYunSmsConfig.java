package io.github.zhaofanzhe.scaffold.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "sms.aliyun")
public class AliYunSmsConfig {

    /**
     * 资源key
     */
    private String accessKeyId;

    /**
     * 安全key
     */
    private String accessKeySecret;

}
