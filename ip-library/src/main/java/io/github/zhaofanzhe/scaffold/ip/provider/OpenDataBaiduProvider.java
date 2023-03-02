package io.github.zhaofanzhe.scaffold.ip.provider;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryAddress;
import io.github.zhaofanzhe.scaffold.ip.IpLibraryProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"DuplicatedCode", "HttpUrlsUsage", "VulnerableCodeUsages"})
public class OpenDataBaiduProvider implements IpLibraryProvider {

    private static final String url = "http://opendata.baidu.com/api.php?query=%s&co=&resource_id=6006&oe=utf8";

    @Override
    public String name() {
        return "OPEN_DATA_BAIDU_COM";
    }

    @Override
    public IpLibraryAddress analysis(String ipAddress) {
        final String result = HttpUtil.get(url.formatted(ipAddress));
        final JSONObject object = JSONUtil.parseObj(result);
        final String status = object.getStr("status");
        if (!"0".equals(status)) {
            return null;
        }

        final IpLibraryAddress address = new IpLibraryAddress();
        address.setChannel(name());
        address.setCountry("中国");

        final JSONArray data = object.getJSONArray("data");
        if (data.size() == 0){
            return null;
        }
        final JSONObject data0 = data.getJSONObject(0);
        final String location = data0.getStr("location");

        final String addr = location.split(" ")[0];

        final Pattern patternProvince = Pattern.compile("(.*?省)");
        final Pattern patternCity = Pattern.compile("省(.*?市)");

        // 匹配省
        final Matcher matcherProvince = patternProvince.matcher(addr);
        if (matcherProvince.find()){
            address.setProvince(matcherProvince.group(1));
        }

        // 匹配市
        final Matcher matcherCity = patternCity.matcher(addr);
        if (matcherCity.find()){
            if (address.getProvince().equals("")){
                address.setProvince(matcherCity.group(1));
            }
            address.setCity(matcherCity.group(1));
        }

        return address;
    }

}
