package io.github.zhaofanzhe.webtest.auth;

import io.github.zhaofanzhe.scaffold.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class AuthAdvice<S extends Session> {

    private final AuthService<S> authService;

    /**
     * 授权模块
     */
    @ModelAttribute(binding = false)
    public Auth auth() {
        return authService.load(Auth.class);
    }

}
