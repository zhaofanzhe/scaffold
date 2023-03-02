package com.github.zhaofanzhe.scaffold.ip;

import com.github.zhaofanzhe.scaffold.ip.provider.FreeApiIpIpProvider;
import com.github.zhaofanzhe.scaffold.ip.provider.IpApiProvider;
import com.github.zhaofanzhe.scaffold.ip.provider.OpenDataBaiduProvider;
import com.github.zhaofanzhe.scaffold.ip.provider.WhoisPConLineProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class IpLibraryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public IpLibrary ipLibrary() {
        final ArrayList<IpLibraryProvider> providers = new ArrayList<>();
        providers.add(new IpApiProvider());
        providers.add(new WhoisPConLineProvider());
        providers.add(new OpenDataBaiduProvider());
        providers.add(new FreeApiIpIpProvider());
        return new IpLibrary(providers);
    }

}
