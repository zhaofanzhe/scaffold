package com.github.zhaofanzhe.webtest.controller;

import com.github.zhaofanzhe.scaffold.lock.RedisLock;
import com.github.zhaofanzhe.scaffold.lock.Type;
import com.github.zhaofanzhe.scaffold.toolkit.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisLockController {

    @RedisLock(type = Type.System, key = "Lock")
    @GetMapping("/lock")
    public Result lock() throws InterruptedException {
        Thread.sleep(3000);
        return Result.success();
    }

}
