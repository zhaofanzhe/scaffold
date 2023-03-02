package com.github.zhaofanzhe.scaffold.wx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatermarkModel {

    /**
     * 用户获取手机号操作的时间戳
     */
    private Integer timestamp;

    /**
     * 小程序appid
     */
    private String appid;

}
