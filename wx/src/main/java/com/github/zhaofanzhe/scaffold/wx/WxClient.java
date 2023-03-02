package com.github.zhaofanzhe.scaffold.wx;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.github.zhaofanzhe.scaffold.wx.model.AccessTokenModel;
import com.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
import com.github.zhaofanzhe.scaffold.wx.model.Code2SessionModel;
import com.github.zhaofanzhe.scaffold.wx.model.UserPhoneNumberModel;
import com.github.zhaofanzhe.scaffold.wx.wxconfig.WxAppConfig;
import com.github.zhaofanzhe.scaffold.wx.wxconfig.WxMerchantConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WxClient {

    private final WxAppConfig wxAppConfig;

    private final WxMerchantConfig wxMerchantConfig;

    public WxClient(WxAppConfig wxAppConfig) {
        this(wxAppConfig, null);
    }

    public WxClient(WxAppConfig wxAppConfig, WxMerchantConfig wxMerchantConfig) {
        this.wxAppConfig = wxAppConfig;
        this.wxMerchantConfig = wxMerchantConfig;
    }

    public Code2SessionModel code2Session(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", wxAppConfig.getAppId(), wxAppConfig.getSecret(), code);
        final String json = HttpUtil.get(url);
        System.out.println(json);
        return JSONUtil.toBean(json, Code2SessionModel.class);
    }

    /**
     * 获取用户手机号
     *
     * @param code 手机号获取凭证
     * @return 手机号信息
     */
    public UserPhoneNumberModel getUserPhoneNumber(String code) {
        final String accessToken = getAccessToken();
        final String url = String.format("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s", accessToken);
        final HttpRequest request = HttpUtil.createPost(url);
        request.body(JSONUtil.toJsonStr(Map.of("code", code)), ContentType.JSON.getValue());
        final String json = request.execute().body();
        System.out.println(json);
        return JSONUtil.toBean(json, UserPhoneNumberModel.class);
    }

    /**
     * 获取 AccessToken
     *
     * @return AccessToken
     */
    public String getAccessToken() {
        StringRedisTemplate redisTemplate = Objects.requireNonNull(SpringContextHolder.getBean(StringRedisTemplate.class));
        final String key = String.format("AccessToken:%s", wxAppConfig.getAppId());
        final ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String accessToken = ops.get(key);
        if (StrUtil.isEmpty(accessToken)) {
            synchronized (this) {
                accessToken = ops.get(key);
                if (StrUtil.isEmpty(accessToken)) {
                    final AccessTokenModel model = requestAccessToken();
                    ops.set(key, model.getAccessToken(), model.getExpiresIn() - 10, TimeUnit.SECONDS);
                    accessToken = model.getAccessToken();
                }
            }
        }
        return accessToken;
    }

    private AccessTokenModel requestAccessToken() {
        final String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", wxAppConfig.getAppId(), wxAppConfig.getSecret());
        final String json = HttpUtil.get(url);
        System.out.println(json);
        return JSONUtil.toBean(json, AccessTokenModel.class);
    }

    public WxAppConfig getWxAppConfig() {
        return wxAppConfig;
    }

    public WxMerchantConfig getWxMerchantConfig() {
        return wxMerchantConfig;
    }

}
