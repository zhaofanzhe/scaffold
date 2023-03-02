package io.github.zhaofanzhe.webtest.controller;

import io.github.zhaofanzhe.scaffold.storage.AliYunStorage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @PostMapping("/storage/upload1")
    public AliYunStorage upload1(@RequestParam AliYunStorage storage) {
        return storage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Upload2Params {
        private AliYunStorage storage;
    }

    @PostMapping("/storage/upload2")
    public Object upload2(@RequestBody Upload2Params params) {
        return params;
    }

}
