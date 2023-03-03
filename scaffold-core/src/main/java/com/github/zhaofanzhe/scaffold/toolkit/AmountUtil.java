package com.github.zhaofanzhe.scaffold.toolkit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtil {

    public static String toWxAmountString(BigDecimal amount) {
        return Integer.toString(toWxAmountInt(amount));
    }

    public static int toWxAmountInt(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.DOWN).intValue();
    }

}
