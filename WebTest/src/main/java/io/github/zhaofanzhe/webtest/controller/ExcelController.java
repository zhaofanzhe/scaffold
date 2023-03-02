package io.github.zhaofanzhe.webtest.controller;

import cn.hutool.core.map.MapUtil;
import io.github.zhaofanzhe.scaffold.excel.Excel;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ExcelController {

    @GetMapping("/excel")
    public ResponseEntity<Resource> excel() throws IOException {
        return Excel.xlsx()
                .nextSheet()
                .writeAuto("测试", 2).newLine()
                .table(List.of(
                        MapUtil.ofEntries(
                                MapUtil.entry("name", "张三"),
                                MapUtil.entry("age", 18)
                        ),
                        MapUtil.ofEntries(
                                MapUtil.entry("name", "李四"),
                                MapUtil.entry("age", 17)
                        ),
                        MapUtil.ofEntries(
                                MapUtil.entry("name", "王五"),
                                MapUtil.entry("age", 20)
                        )
                ))
                .addHeaderAlias("name", "张三")
                .addHeaderAlias("age", "李四")
                .returnExcel()
                .exportResponseEntity("test");
    }

}
