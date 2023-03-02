package io.github.zhaofanzhe.scaffold.wx;

import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxApiType;

import java.io.InputStream;
import java.util.Map;

public class AppWxPayApi extends WxPayApi {

    /**
     * 请求单次分账
     *
     * @param params   请求参数
     * @param certPath 证书文件路径
     * @param certPass 证书密码
     * @param protocol 协议
     * @return {@link String} 请求返回的结果
     */
    public static String profitSharingByProtocol(Map<String, String> params, String certPath, String certPass, String protocol) {
        return executionByProtocol(getReqUrl(WxApiType.PROFIT_SHARING), params, certPath, certPass, protocol);
    }

    /**
     * 请求单次分账
     *
     * @param params   请求参数
     * @param certFile 证书文件的  InputStream
     * @param certPass 证书密码
     * @param protocol 协议
     * @return {@link String} 请求返回的结果
     */
    public static String profitSharingByProtocol(Map<String, String> params, InputStream certFile, String certPass, String protocol) {
        return executionByProtocol(getReqUrl(WxApiType.PROFIT_SHARING), params, certFile, certPass, protocol);
    }

    /**
     * 完结分账
     *
     * @param params   请求参数
     * @param certPath 证书文件路径
     * @param certPass 证书密码
     * @param protocol 协议
     * @return {@link String} 请求返回的结果
     */
    public static String profitSharingFinishByProtocol(Map<String, String> params, String certPath, String certPass, String protocol) {
        return executionByProtocol(getReqUrl(WxApiType.PROFIT_SHARING_FINISH), params, certPath, certPass, protocol);
    }


    /**
     * 完结分账
     *
     * @param params   请求参数
     * @param certFile 证书文件的  InputStream
     * @param certPass 证书密码
     * @param protocol 协议
     * @return {@link String} 请求返回的结果
     */
    public static String profitSharingFinishByProtocol(Map<String, String> params, InputStream certFile, String certPass, String protocol) {
        return executionByProtocol(getReqUrl(WxApiType.PROFIT_SHARING_FINISH), params, certFile, certPass, protocol);
    }

    public static String transfers(Map<String, String> params, InputStream certFile, String certPass, String protocol) {
        return executionByProtocol(getReqUrl(WxApiType.TRANSFER, null, false), params, certFile, certPass, protocol);
    }

}
