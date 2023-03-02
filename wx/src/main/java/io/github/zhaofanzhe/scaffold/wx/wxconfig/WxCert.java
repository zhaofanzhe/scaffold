package io.github.zhaofanzhe.scaffold.wx.wxconfig;

import cn.hutool.core.codec.Base64;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class WxCert {

    private String p12CertBase64File;

    private byte[] certBytes = null;

    public void setP12CertBase64File(String p12CertBase64File) {
        this.p12CertBase64File = p12CertBase64File;
    }

    public String getP12CertBase64File() {
        return p12CertBase64File;
    }

    @SneakyThrows
    public InputStream getCertInputStream() {
        if (this.certBytes == null) {
            this.initCertBytes();
        }
        return new ByteArrayInputStream(this.certBytes);
    }

    private synchronized void initCertBytes() throws IOException {
        if (this.certBytes != null) {
            return;
        }
        byte[] certBytes = Files.readAllBytes(Path.of(this.p12CertBase64File));
        this.certBytes = Base64.decode(certBytes);
    }

}
