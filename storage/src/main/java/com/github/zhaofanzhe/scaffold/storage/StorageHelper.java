package com.github.zhaofanzhe.scaffold.storage;

public class StorageHelper {

    // 拓展名白名单
    private static final String[] Whitelist = {
            ".tar.gz"
    };

    /**
     * 获取文件名拓展
     */
    public static String getFilenameExtension(String filename) {
        String name = filename.toLowerCase();
        for (String it : Whitelist) {
            if (name.endsWith(it)) {
                return it;
            }
        }
        final String[] array = name.split("\\.");
        return array[array.length - 1].toLowerCase();
    }

}
