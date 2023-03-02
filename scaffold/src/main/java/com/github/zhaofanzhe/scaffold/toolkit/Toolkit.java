package com.github.zhaofanzhe.scaffold.toolkit;

import java.time.Duration;
import java.util.Optional;
import java.util.regex.Pattern;

public class Toolkit {

    static final Pattern PHONE_PATTERN = Pattern.compile("^1\\d{10}$");

    /**
     * 是否为手机号
     *
     * @param phone 手机号码
     * @return 是否为手机号
     */
    public static boolean isPhoneNumber(String phone) {
        if (phone == null) return false;
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 格式化时间间隔
     *
     * @param duration 时间间隔
     * @return 格式化字符串
     */
    public static String format(Duration duration) {
        if (duration.toDays() > 0) {
            return String.format("%d天", duration.toDays());
        } else if (duration.toHours() > 0) {
            return String.format("%d小时", duration.toHours());
        } else if (duration.toMinutes() > 0) {
            return String.format("%d分钟", duration.toMinutes());
        } else if (duration.toSeconds() > 0) {
            return String.format("%d秒", duration.toSeconds());
        } else {
            return null;
        }
    }

    public static String formatPassing(Duration duration) {
        final String text = format(duration.abs());
        if (text == null) {
            return "刚刚";
        }
        return String.format("%s前", text);
    }

}
