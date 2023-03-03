package com.github.zhaofanzhe.scaffold.advice;

import com.github.zhaofanzhe.scaffold.exception.BusinessInterruptionException;
import com.github.zhaofanzhe.scaffold.toolkit.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public Result handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return Result.fail(Result.PARAM_ERROR).message("数据校验失败").error(errors);
    }

    @ExceptionHandler
    public Result handle(MissingServletRequestParameterException e) {
        return Result.fail(Result.PARAM_ERROR).error(String.format("缺少参数 %s", e.getParameterName()));
    }

    @ExceptionHandler
    public Result handle(MethodArgumentTypeMismatchException e) {
        return Result.fail(Result.PARAM_ERROR).error(String.format("参数 %s 格式异常", e.getName()));
    }

    /**
     * 处理 业务中断异常
     */
    @ExceptionHandler
    public Object handleBusinessInterruptionException(BusinessInterruptionException exception) {
        return exception.getPayload();
    }

}
