package com.github.zhaofanzhe.scaffold.ip;

public class IpLibraryAddress {

    /**
     * 解析渠道
     */
    private String channel = "";

    /**
     * 国家
     */
    private String country = "";

    /**
     * 省
     */
    private String province = "";

    /**
     * 市
     */
    private String city = "";

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "%s/%s/%s".formatted(this.country, this.province, this.city);
    }
}
