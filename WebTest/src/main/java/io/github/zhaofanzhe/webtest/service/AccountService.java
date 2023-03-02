package io.github.zhaofanzhe.webtest.service;

import io.github.zhaofanzhe.webtest.mapper.AccountMapper;
import io.github.zhaofanzhe.webtest.model.Account;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class AccountService extends ServiceImpl<AccountMapper, Account> {

}


