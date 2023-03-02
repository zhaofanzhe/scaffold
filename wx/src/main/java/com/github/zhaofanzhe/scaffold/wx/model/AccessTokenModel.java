package com.github.zhaofanzhe.scaffold.wx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenModel {

    /**
     * 获取到的凭证
     */
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    private Integer expiresIn;


}
