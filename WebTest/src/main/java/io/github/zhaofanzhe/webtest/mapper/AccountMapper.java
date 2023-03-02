package io.github.zhaofanzhe.webtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhaofanzhe.webtest.model.Account;

import java.util.Map;

public interface AccountMapper extends BaseMapper<Account> {
    Map<String, Object> test3();
}