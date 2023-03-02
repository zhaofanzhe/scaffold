package io.github.zhaofanzhe.scaffold.storage.secure;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.net.url.UrlQuery;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("DuplicatedCode")
public class StorageId extends Attributes {

    public static StorageId parse(String storageId) {
        storageId = Base64.decodeStr(storageId);

        final StorageId _storageId = new StorageId();

        UrlQuery query = UrlQuery.of(storageId, StandardCharsets.UTF_8);

        query.getQueryMap().forEach((key, value) -> {
            _storageId.set((String) key, (String) value);
        });

        return _storageId;
    }

    public String getStorageId() {
        return Base64.encode(build());
    }

}
