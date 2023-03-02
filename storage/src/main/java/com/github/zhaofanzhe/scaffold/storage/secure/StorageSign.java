package com.github.zhaofanzhe.scaffold.storage.secure;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

public abstract class StorageSign extends Attributes {

    private static final Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, DefaultKey.privateKey, DefaultKey.publicKey);

    /**
     * 生成签名信息
     */
    String sign() {
        // 取签名前32位
        return sign.signHex(build("sign")).substring(0, 32);
    }

    public boolean verifySign() {
        return sign().equals(getOrDefault("sign", ""));
    }

}
