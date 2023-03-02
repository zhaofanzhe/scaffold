package com.github.zhaofanzhe.scaffold.wx.wxconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxAppConfig {

    /**
     * AppID
     */
    private String appId;

    /**
     * Secret
     */
    private String secret;

}
