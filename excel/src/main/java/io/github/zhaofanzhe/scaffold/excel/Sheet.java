package io.github.zhaofanzhe.scaffold.excel;

import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Consumer;

public class Sheet {

    private final Excel excel;

    private final ExcelWriter writer;

    private int lastRow = -1;

    private int lastColumn = -1;

    private int row;

    private int column;

    protected Sheet(Excel excel, ExcelWriter writer) {
        this.excel = excel;
        this.writer = writer;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.lastRow = this.row;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.lastColumn = column;
        this.column = column;
    }

    public Sheet writeAuto(Object content) {
        return this.writeAuto(content, 1);
    }

    public Sheet writeAuto(Object content, boolean isSetHeaderStyle) {
        return this.writeAuto(content, 1, isSetHeaderStyle);
    }

    public Sheet writeAuto(Object content, CellStyle cellStyle) {
        return this.writeAuto(content, 1, cellStyle);
    }

    public Sheet writeAuto(Object content, int columnSize) {
        return this.writeAuto(content, 1, columnSize);
    }

    public Sheet writeAuto(Object content, int columnSize, boolean isSetHeaderStyle) {
        return this.writeAuto(content, 1, columnSize, isSetHeaderStyle);
    }

    public Sheet writeAuto(Object content, int columnSize, CellStyle cellStyle) {
        return this.writeAuto(content, 1, columnSize, cellStyle);
    }

    public Sheet writeAuto(Object content, int rowSize, int columnSize) {
        return this.write(this.row, this.column, content, rowSize, columnSize);
    }

    public Sheet writeAuto(Object content, int rowSize, int columnSize, boolean isSetHeaderStyle) {
        return this.write(this.row, this.column, content, rowSize, columnSize, isSetHeaderStyle);
    }

    public Sheet writeAuto(Object content, int rowSize, int columnSize, CellStyle cellStyle) {
        return this.write(this.row, this.column, content, rowSize, columnSize, cellStyle);
    }

    public Sheet write(int row, int column, Object content) {
        return this.write(row, column, content, 1, 1);
    }

    public Sheet write(int row, int column, Object content, boolean isSetHeaderStyle) {
        return this.write(row, column, content, 1, 1, isSetHeaderStyle);
    }

    public Sheet write(int row, int column, Object content, CellStyle cellStyle) {
        return this.write(row, column, content, 1, 1, cellStyle);
    }

    public Sheet write(int row, int column, Object content, int columnSize) {
        return this.write(row, column, content, 1, columnSize);
    }

    public Sheet write(int row, int column, Object content, int columnSize, boolean isSetHeaderStyle) {
        return this.write(row, column, content, 1, columnSize, isSetHeaderStyle);
    }

    public Sheet write(int row, int column, Object content, int columnSize, CellStyle cellStyle) {
        return this.write(row, column, content, 1, columnSize, cellStyle);
    }

    public Sheet write(int row, int column, Object content, int rowSize, int columnSize) {
        return this.write(row, column, content, rowSize, columnSize, false);
    }

    public Sheet write(int row, int column, Object content, int rowSize, int columnSize, boolean isSetHeaderStyle) {
        return this.write(row, column, content, rowSize, columnSize, this.writer.getStyleSet().getStyleByValueType(content, isSetHeaderStyle));
    }

    public Sheet write(int row, int column, Object content, int rowSize, int columnSize, CellStyle cellStyle) {
        if (rowSize <= 0) throw new RuntimeException("rowSize不能小于等于0");
        if (columnSize <= 0) throw new RuntimeException("columnSize不能小于等于0");
        if (rowSize == 1 && columnSize == 1) {
            this.writer.writeCellValue(column, row, content);
            this.writer.setStyle(cellStyle, column, row);
        } else {
            this.writer.merge(row, row + rowSize - 1, column, column + columnSize - 1, content, cellStyle);
        }
        this.lastRow = this.row;
        this.row = row + rowSize - 1;
        this.lastColumn = this.column;
        this.column = column + columnSize;
        return this;
    }

    public Sheet newLine() {
        this.row++;
        this.column = 0;
        return this;
    }

    public Table table(Iterable<?> iterable) {
        return new Table(this, iterable);
    }

    public Sheet table(Consumer<Table> consumer, Iterable<?> iterable) {
        Table table = table(iterable);
        consumer.accept(table);
        table.onWrite(this);
        return this;
    }

    public Sheet lastRowHeight(int height) {
        if (lastRow == -1) {
            return this;
        }
        return this.rowHeight(this.lastRow, height);
    }

    public Sheet rowHeight(int height) {
        return this.rowHeight(this.row, height);
    }

    public Sheet rowHeight(int startRow, int endRow, int height) {
        if (startRow > endRow) throw new RuntimeException("fail startRow > endRow");
        for (int i = startRow; i <= endRow; i++) {
            this.rowHeight(i, height);
        }
        return this;
    }

    public Sheet rowHeight(int row, int height) {
        this.writer.setRowHeight(row, height);
        return this;
    }

    public Sheet lastColumnWidth(int width) {
        if (this.lastColumn == -1) {
            return this;
        }
        return this.columnWidth(this.column, width);
    }

    public Sheet columnWidth(int width) {
        return this.columnWidth(this.column, width);
    }

    public Sheet columnWidth(int startColumn, int endColumn, int width) {
        if (startColumn > endColumn) throw new RuntimeException("fail startColumn > endColumn");
        for (int i = startColumn; i <= endColumn; i++) {
            this.columnWidth(i, width);
        }
        return this;
    }

    public Sheet columnWidth(int column, int width) {
        this.writer.setColumnWidth(column, width);
        return this;
    }

    public CellStyle createCellStyle(){
        return this.writer.createCellStyle();
    }

    public Excel returnExcel() {
        return this.excel;
    }

}
