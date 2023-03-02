package com.github.zhaofanzhe.scaffold.password;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 密码工具类
 * 创建密码 Password.create(password).toString();
 * 校验密码 new Password(hash).verify(password);
 */
public class Password {
    // Hash值
    private String hash;

    public Password(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * 校验
     *
     * @param password 密码
     * @return 校验是否成功
     */
    public boolean verify(String password) {
        return BCrypt.checkpw(password, hash);
    }

    /**
     * 创建 Password
     *
     * @param password 密码
     * @return 密码类
     */
    public static Password create(String password) {
        return new Password(BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    @Override
    public String toString() {
        return hash;
    }
}
