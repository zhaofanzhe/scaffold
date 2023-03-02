package com.github.zhaofanzhe.scaffold.wx;

import com.github.zhaofanzhe.scaffold.wx.wxconfig.WxAppConfig;
import com.github.zhaofanzhe.scaffold.wx.wxconfig.WxMerchantConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("wx")
public class WxConfig {

    private Map<String, WxAppConfig> apps;

    private Map<String, WxMerchantConfig> merchants;

    public WxAppConfig findAppConfig(String appName) {
        return this.apps.get(appName);
    }

    public WxMerchantConfig findMerchantConfig(String merchantName) {
        return this.merchants.get(merchantName);
    }

}
