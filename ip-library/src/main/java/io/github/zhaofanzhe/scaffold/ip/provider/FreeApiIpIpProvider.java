package io.github.zhaofanzhe.scaffold.ip.provider;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryAddress;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryProvider;

@SuppressWarnings({"DuplicatedCode", "HttpUrlsUsage"})
public class FreeApiIpIpProvider implements IpLibraryProvider {

    private static final String url = "http://freeapi.ipip.net/%s";

    @Override
    public String name() {
        return "FREE_API_IP_IP";
    }

    @Override
    public IpLibraryAddress analysis(String ipAddress) {
        final String result = HttpUtil.get(url.formatted(ipAddress));
        final JSONArray arr = JSONUtil.parseArray(result);
        final IpLibraryAddress address = new IpLibraryAddress();
        address.setChannel(name());
        address.setCountry(arr.getStr(0));
        address.setProvince(arr.getStr(1));
        address.setCity(arr.getStr(2));
        return address;
    }

}
