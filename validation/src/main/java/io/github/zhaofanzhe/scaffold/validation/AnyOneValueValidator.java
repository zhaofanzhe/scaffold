package io.github.zhaofanzhe.scaffold.validation;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnyOneValueValidator implements ConstraintValidator<AnyOneValue, String> {

    public boolean required = false;

    private String[] options;

    @Override
    public void initialize(AnyOneValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.options = constraintAnnotation.options();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 不是必须的 && 字符串为空 = 直接返回 true
        if (!required && StrUtil.isEmpty(value)) {
            return true;
        }
        for (String option : this.options) {
            if (StrUtil.equals(option, value)) {
                return true;
            }
        }
        return false;
    }

}
