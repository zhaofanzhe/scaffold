package com.github.zhaofanzhe.scaffold.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendSmsParams {

    /**
     * 手机号
     */
    private List<String> phones;

    /**
     * 签名
     */
    private String autograph;

    /**
     * 模板代码
     */
    private String templateCode;

    /**
     * 模板参数
     */
    private Map<String, String> templateParams;

}
