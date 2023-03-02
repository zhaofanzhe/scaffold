package com.github.zhaofanzhe.webtest.controller;

import com.github.zhaofanzhe.scaffold.toolkit.RandomCode;
import com.github.zhaofanzhe.scaffold.toolkit.Result;
import com.github.zhaofanzhe.webtest.model.Account;
import com.github.zhaofanzhe.webtest.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final AccountService accountService;

    /**
     * 抛出异常 事务测试
     */
    @Transactional
    @GetMapping("/test1")
    public Result test1() {

        final String code = RandomCode.nextCode(10);

        final Account account = Account.builder()
                .phone(code)
                .build();

        accountService.save(account);

        final char first = code.charAt(0);

        switch (first) {
            case '0', '1', '2', '3', '4' -> {
                log.info("test1 回滚了");
                throw new RuntimeException("test1 回滚了");
            }
            default -> {
                log.info("test1 通过了");
            }
        }

        return Result.success();
    }

    /**
     * 手动回滚 事务测试
     */
    @Transactional
    @GetMapping("/test2")
    public Result test2() {

        final String code = RandomCode.nextCode(10);

        final Account account = Account.builder()
                .phone(code)
                .build();

        accountService.save(account);

        final char first = code.charAt(0);

        switch (first) {
            case '0', '1', '2', '3', '4' -> {
                log.info("test2 回滚了");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            default -> {
                log.info("test2 通过了");
            }
        }

        return Result.success();
    }

    /**
     * 接收 map 字段蛇形转小驼峰
     */
    @GetMapping("/test3")
    public Result test3() {
        Map<String, Object> result = accountService.getBaseMapper().test3();
        return Result.success().payload(result);
    }

}
