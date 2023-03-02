package io.github.zhaofanzhe.scaffold.storage.secure;

import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.core.util.URLUtil;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SuppressWarnings("DuplicatedCode")
public class LocalStorageUrl extends StorageSign {

    public static LocalStorageUrl parse(String url) {
        LocalStorageUrl localStorageUrl = new LocalStorageUrl();

        final URL _url = URLUtil.url(url);

        localStorageUrl.set("url", String.format("%s://%s%s", _url.getProtocol(), _url.getAuthority(), _url.getPath()));

        UrlQuery query = UrlQuery.of(_url.getQuery(), StandardCharsets.UTF_8);

        query.getQueryMap().forEach((key, value) -> localStorageUrl.set((String) key, (String) value));

        return localStorageUrl;
    }

    public void setUrl(String url) {
        set("url", url);
    }

    public String getUrl() {
        return get("url");
    }

    public void setExpire(LocalDateTime expire) {
        set("expire", String.valueOf(expire.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
    }

    public LocalDateTime getExpire() {
        String expire = get("expire");
        if (expire == null) return null;
        long value;
        try {
            value = Long.parseLong(expire);
        } catch (NumberFormatException e) {
            return null;
        }
        return Instant.ofEpochMilli(value).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    public String build() {
        set("sign", sign());
        return String.format("%s?%s", getUrl(), build("url"));
    }

    public boolean verifyExpire() {
        LocalDateTime expire = getExpire();
        if (expire == null) return true;
        return LocalDateTime.now().isBefore(expire);
    }

}
