package com.github.zhaofanzhe.scaffold.wx.decrypt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxEncrypted {

    private String encryptedData;

    private String iv;

}
