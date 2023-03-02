package io.github.zhaofanzhe.scaffold.wx.wxconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxMerchantConfig {

    private String mchId;

    private String key;

    private String v3Key;

    private WxCert cert;

}
