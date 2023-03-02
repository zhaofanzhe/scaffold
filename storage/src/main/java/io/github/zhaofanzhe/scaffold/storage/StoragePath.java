package io.github.zhaofanzhe.scaffold.storage;

import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public class StoragePath {

    private final String dirPath;

    private final String filePath;

    public StoragePath(String dirPath, String filePath) {
        this.dirPath = dirPath;
        this.filePath = filePath;
    }

    public String getDirPath() {
        return dirPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static StoragePath path(String originalFileName) {
        String filenameExtension = StorageHelper.getFilenameExtension(originalFileName);

        LocalDateTime now = LocalDateTime.now();

        String name = UUID.randomUUID().toString().replace("-", "");

        String dirPath = String.format("/%d/%d/%d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        String filePath = String.format("%s/%s.%s", dirPath, name, filenameExtension);

        return new StoragePath(dirPath, filePath);
    }

}
