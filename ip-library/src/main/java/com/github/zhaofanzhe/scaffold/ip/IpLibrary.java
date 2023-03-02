package com.github.zhaofanzhe.scaffold.ip;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class IpLibrary {

    private final List<IpLibraryProvider> providers;

    public IpLibrary(List<IpLibraryProvider> providers) {
        this.providers = providers;
    }

    public IpLibraryAddress analysis(String providerName, String ipAddress) {
        final Optional<IpLibraryProvider> optional = providers.stream().filter(provider -> provider.name().equals(providerName)).findFirst();
        return optional.map(provider -> provider.analysis(ipAddress)).orElse(null);
    }

    public IpLibraryAddress analysis(IpLibraryProvider provider, String ipAddress) {
        return provider.analysis(ipAddress);
    }

    public IpLibraryAddress analysis(String ipAddress) {
        final ArrayList<IpLibraryProvider> list = new ArrayList<>(providers);
        while (list.size() > 0) {
            int index = (int) (Math.random() * list.size());
            if (index == list.size()) {
                index--;
            }
            final IpLibraryProvider provider = list.remove(index);
            final String name = provider.name();
            try {
                final IpLibraryAddress address = provider.analysis(ipAddress);
                if (address != null) {
                    return address;
                }
                log.info("解析失败: {}", name);
            } catch (Exception e) {
                log.info("解析失败: {}", name);
                e.printStackTrace();
            }
        }
        return null;
    }

}
