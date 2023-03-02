package io.github.zhaofanzhe.scaffold.wx.decrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;

public class WxDecrypt {

    public static String decrypt(String sessionKey, WxEncrypted encrypted) {
        final byte[] aesKey = Base64.decode(sessionKey);
        final byte[] encryptedData = Base64.decode(encrypted.getEncryptedData());
        final byte[] iv = Base64.decode(encrypted.getIv());
        AES aes = new AES("CBC", "PKCS7Padding", aesKey, iv);
        return new String(aes.decrypt(encryptedData));
    }

    public static <T> T decrypt(String sessionKey, WxEncrypted encrypted, Class<T> clazz) {
        try {
            final String decrypt = decrypt(sessionKey, encrypted);
            return JSONUtil.toBean(decrypt, clazz);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

}
