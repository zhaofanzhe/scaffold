package com.github.zhaofanzhe.scaffold.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

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

    public void setPath(String path) {
        // 如果不是绝对路径, 转换为绝对路径
        // MultipartFile.transferTo(File) 的特性, 不是绝对路径会拼接 MultipartFile 的路径
        // By the way, MultipartFile.transferTo(Path) 没有这个特性
        // 因此需要转换为绝对路径
        final File file = new File(path);
        if (!file.isAbsolute()) {
            path = file.getAbsolutePath();
        }
        this.path = path;
    }
}
