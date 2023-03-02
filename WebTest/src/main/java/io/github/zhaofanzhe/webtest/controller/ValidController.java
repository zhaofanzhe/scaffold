package io.github.zhaofanzhe.webtest.controller;

import io.github.zhaofanzhe.scaffold.validation.PhoneNumber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
public class ValidController {

    public static class TestParams {

        @NotEmpty
        private String name;

        @PhoneNumber
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "TestParams{" +
                   "name='" + name + '\'' +
                   ", phone='" + phone + '\'' +
                   '}';
        }
    }

    @GetMapping("/valid/test")
    public String test(@Valid TestParams params){
        System.out.println(params);
        return "Test";
    }

}
