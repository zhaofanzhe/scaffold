package io.github.zhaofanzhe.scaffold.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AuthService.class})
@Configuration
public class AuthAutoConfiguration {
}
