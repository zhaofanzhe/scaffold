package io.github.zhaofanzhe.webtest.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import io.github.zhaofanzhe.scaffold.storage.AliYunStorage;
import io.github.zhaofanzhe.scaffold.toolkit.Result;
import io.github.zhaofanzhe.webtest.model.Account;
import io.github.zhaofanzhe.webtest.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterParams {
        private String phone;
        private String password;
        private AliYunStorage avatar;
    }

    @PostMapping("/accounts/_register")
    public Result register(RegisterParams params) {
        final Account account = BeanUtil.copyProperties(params, Account.class);
        accountService.save(account);
        return Result.success().payload(account);
    }

    @GetMapping("/accounts")
    public Result list() {
        final LambdaQueryChainWrapper<Account> query = accountService.lambdaQuery();
        final List<Account> list = query.list();
        return Result.success().list(list);
    }

}
