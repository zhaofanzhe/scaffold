package com.github.zhaofanzhe.scaffold.excel;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Excel {

    private final ExcelWriter writer;

    private final boolean isXlsx;

    private final Map<String, Sheet> sheets = new LinkedHashMap<>();

    private Sheet sheet = null;

    public static Excel xlsx() {
        return new Excel(true);
    }

    public static Excel xls() {
        return new Excel(false);
    }

    private Excel(boolean isXlsx) {
        this.isXlsx = isXlsx;
        this.writer = ExcelUtil.getWriter(isXlsx);
        this.writer.setOnlyAlias(true);
    }

    private String nextSheetName() {
        return String.format("sheet%s", this.sheets.size() + 1);
    }

    public Sheet nextSheet() {
        return useSheet(nextSheetName());
    }

    public Excel nextSheet(Consumer<Sheet> consumer) {
        return useSheet(nextSheetName(), consumer);
    }

    public Sheet useSheet(String sheet) {
        this.sheet = this.sheets.get(sheet);
        if (this.sheet == null) {
            if (this.sheets.isEmpty()) {
                this.writer.renameSheet(0, sheet);
            }
            this.sheet = new Sheet(this, this.writer);
            this.sheets.put(sheet, this.sheet);
        }
        this.writer.setSheet(sheet);
        return this.sheet;
    }

    public Excel useSheet(String sheet, Consumer<Sheet> consumer) {
        consumer.accept(this.useSheet(sheet));
        return this;
    }

    public CellStyle createCellStyle(){
        return this.writer.createCellStyle();
    }

    public Excel doNative(Consumer<ExcelWriter> doNative) {
        doNative.accept(this.writer);
        return this;
    }

    /**
     * 导出响应
     *
     * @param filename 文件名,不包含后缀
     * @return 相应数据
     * @throws IOException io异常
     */
    public ResponseEntity<Resource> exportResponseEntity(String filename) throws IOException {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writer.flush(outputStream);
        writer.close();
        final ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        outputStream.close();

        if (this.isXlsx) {
            filename = filename + ".xlsx";
        } else {
            filename = filename + ".xls";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", String.format("attachment; filename= %s", URLEncodeUtil.encode(filename)));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
