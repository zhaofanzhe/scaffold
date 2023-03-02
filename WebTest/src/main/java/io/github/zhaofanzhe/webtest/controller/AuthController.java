package io.github.zhaofanzhe.webtest.controller;

import io.github.zhaofanzhe.webtest.auth.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth/login")
    public String login(Auth auth, @RequestParam Integer id) {
        auth.login("USER", "", id);
        return "SUCCESS";
    }

    @GetMapping("/auth/current")
    public String login(Auth auth) {
        if (auth.isGenre("USER")){
            return auth.getId().toString();
        }
        return "未登录";
    }

}
