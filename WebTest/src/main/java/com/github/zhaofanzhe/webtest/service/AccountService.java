package com.github.zhaofanzhe.webtest.service;

import com.github.zhaofanzhe.webtest.mapper.AccountMapper;
import com.github.zhaofanzhe.webtest.model.Account;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class AccountService extends ServiceImpl<AccountMapper, Account> {

}


