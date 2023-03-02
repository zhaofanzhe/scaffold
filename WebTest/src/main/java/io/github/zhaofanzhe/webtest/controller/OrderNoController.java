package io.github.zhaofanzhe.webtest.controller;

import io.github.zhaofanzhe.scaffold.orderno.OrderNoGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderNoController {

    private final OrderNoGenerator orderNoGenerator;

    @GetMapping("/orderNo")
    public String orderNo() {
        return orderNoGenerator.next(1);
    }

}
