package com.github.zhaofanzhe.webtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.zhaofanzhe.webtest.model.Account;

import java.util.Map;

public interface AccountMapper extends BaseMapper<Account> {
    Map<String, Object> test3();
}