package com.github.zhaofanzhe.scaffold.validation;

import cn.hutool.core.util.StrUtil;
import com.github.zhaofanzhe.scaffold.toolkit.Toolkit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    public boolean required = false;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 不是必须的 && 字符串为空 = 直接返回 true
        if (!required && StrUtil.isEmpty(value)) {
            return true;
        }
        return Toolkit.isPhoneNumber(value);
    }

}
