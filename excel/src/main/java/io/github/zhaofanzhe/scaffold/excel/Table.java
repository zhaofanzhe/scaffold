package io.github.zhaofanzhe.scaffold.excel;

import cn.hutool.core.bean.BeanUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private static final String SEPARATE = ">";

    private final Sheet sheet;

    private final Iterable<?> iterable;

    private final Map<String, String> headerAliasNames = new LinkedHashMap<>();

    private final Map<String, Integer> headerAliasSizes = new LinkedHashMap<>();

    private int rowHeight = 0;

    public Table(Sheet sheet, Iterable<?> iterable) {
        this.sheet = sheet;
        this.iterable = iterable;
    }

    public Table addHeaderAlias(String name, String alias) {
        return addHeaderAlias(name, alias, 1);
    }

    public Table addHeaderAlias(String name, String alias, int size) {
        return addHeaderAlias(List.of(name), alias, size);
    }

    public Table addHeaderAlias(List<String> names, String alias) {
        return addHeaderAlias(names, alias, 1);
    }

    public Table addHeaderAlias(List<String> names, String alias, int size) {
        for (String name : names) {
            if (name.contains(SEPARATE)) {
                throw new RuntimeException(String.format("name \"%s\" can't contains \"%s\"", name, SEPARATE));
            }
        }
        String finalName = String.join(SEPARATE, names);
        this.headerAliasNames.put(finalName, alias);
        this.headerAliasSizes.put(finalName, size);
        return this;
    }

    public Table rowHeight(int height) {
        this.rowHeight = height;
        return this;
    }

    public Sheet returnSheet() {
        this.onWrite(this.sheet);
        return this.sheet;
    }

    public Excel returnExcel() {
        return this.returnSheet().returnExcel();
    }

    public void onWrite(Sheet sheet) {
        if (sheet.getColumn() != 0) {
            sheet.newLine();
        }
        this.headerAliasNames.forEach((finalName, alias) -> {
            Integer size = this.headerAliasSizes.get(finalName);
            sheet.writeAuto(alias, size, true);
        });
        if (this.rowHeight > 0) {
            sheet.lastRowHeight(this.rowHeight);
        }
        sheet.newLine();
        for (Object object : iterable) {
            this.headerAliasNames.forEach((finalName, alias) -> {
                Integer size = this.headerAliasSizes.get(finalName);
                sheet.writeAuto(getDeepValue(object, finalName), size);
            });
            if (this.rowHeight > 0) {
                sheet.lastRowHeight(this.rowHeight);
            }
            sheet.newLine();
        }
    }

    private Object getDeepValue(Object value, String finaName) {
        String[] names = finaName.split(SEPARATE);
        for (String name : names) {
            if (value == null) {
                return null;
            }
            value = BeanUtil.getFieldValue(value, name);
        }
        return value;
    }

}
