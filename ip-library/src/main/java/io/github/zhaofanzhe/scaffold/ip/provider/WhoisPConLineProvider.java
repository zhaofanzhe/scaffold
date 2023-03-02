package io.github.zhaofanzhe.scaffold.ip.provider;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryAddress;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryProvider;

@SuppressWarnings({"DuplicatedCode", "VulnerableCodeUsages"})
public class WhoisPConLineProvider implements IpLibraryProvider {

    private static final String url = "https://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    @Override
    public String name() {
        return "WHOIS_P_CON_LINE_COM_CN";
    }

    @Override
    public IpLibraryAddress analysis(String ipAddress) {
        final String result = HttpUtil.get(url.formatted(ipAddress));
        final JSONObject object = JSONUtil.parseObj(result);
        final String err = object.getStr("err");
        if (!"".equals(err)) {
            return null;
        }
        final IpLibraryAddress address = new IpLibraryAddress();
        address.setChannel(name());
        address.setCountry("中国");
        address.setProvince(object.getStr("pro"));
        address.setCity(object.getStr("city"));
        return address;
    }

}
