package com.github.zhaofanzhe.scaffold.wx;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

public class WxClientHub {

    private final WxConfig wxConfig;

    private final Map<String, WxClient> clients = new HashMap<>();

    public WxClientHub(WxConfig wxConfig) {
        this.wxConfig = wxConfig;
    }

    public WxClient use(String appName) {
        return use(appName, null);
    }

    public WxClient use(String appName, String mchId) {
        final WxClient client = clients.get(buildKey(appName, mchId));
        if (client != null) {
            return client;
        }
        return newWxClient(appName, mchId);
    }

    private synchronized WxClient newWxClient(String appName, String mchName) {
        final String key = buildKey(appName, mchName);
        WxClient client = clients.get(key);
        if (client != null) {
            return client;
        }
        client = new WxClient(wxConfig.findAppConfig(appName), wxConfig.findMerchantConfig(mchName));
        clients.put(appName, client);
        return client;
    }

    private String buildKey(String appName, String mchName) {
        if (StrUtil.isEmpty(appName)) {
            appName = "_";
        }
        if (StrUtil.isEmpty(mchName)) {
            mchName = "_";
        }
        return String.format("%s:%s", appName, mchName);
    }

}

