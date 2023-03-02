package io.github.zhaofanzhe.scaffold.ip;

public interface IpLibraryProvider {

    String name();

    IpLibraryAddress analysis(String ipAddress);

}
