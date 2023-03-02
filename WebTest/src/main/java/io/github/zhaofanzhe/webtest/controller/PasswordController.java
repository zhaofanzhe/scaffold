package io.github.zhaofanzhe.webtest.controller;

import io.github.zhaofanzhe.scaffold.password.Password;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

    @GetMapping("/password/new")
    public String newPassword(@RequestParam String password) {
        return Password.create(password).getHash();
    }

    @GetMapping("/password/verify")
    public boolean verifyPassword(@RequestParam String hash, @RequestParam String password) {
        return new Password(hash).verify(password);
    }

}
