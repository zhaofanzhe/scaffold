package io.github.zhaofanzhe.scaffold.ip.provider;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryAddress;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryProvider;

@SuppressWarnings({"DuplicatedCode", "HttpUrlsUsage", "VulnerableCodeUsages"})
public class IpApiProvider implements IpLibraryProvider {

    private static final String url = "http://ip-api.com/json/%s?lang=zh-CN";

    @Override
    public String name() {
        return "IP_API_COM";
    }

    @Override
    public IpLibraryAddress analysis(String ipAddress) {
        final String result = HttpUtil.get(url.formatted(ipAddress));
        //noinspection VulnerableCodeUsages
        final JSONObject object = JSONUtil.parseObj(result);
        final String status = object.getStr("status");
        if (!"success".equals(status)) {
            return null;
        }
        final IpLibraryAddress address = new IpLibraryAddress();
        address.setChannel(name());
        address.setCountry(object.getStr("country"));
        address.setProvince(object.getStr("regionName"));
        address.setCity(object.getStr("city"));
        return address;
    }

}
