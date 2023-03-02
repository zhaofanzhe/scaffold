package io.github.zhaofanzhe.scaffold.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {AnyOneValueValidator.class})
public @interface AnyOneValue {

    boolean required() default true;

    String[] options() default {};

    String message() default "无效的可选项";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
