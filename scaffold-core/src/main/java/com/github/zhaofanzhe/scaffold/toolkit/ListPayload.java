package com.github.zhaofanzhe.scaffold.toolkit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.zhaofanzhe.scaffold.mixin.MixIn;
import com.github.zhaofanzhe.scaffold.mixin.MultipleMixIn;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Getter
public class ListPayload<R> {

    /**
     * 页码
     */
    private Long current;

    /**
     * 页大小
     */
    private Long size;

    /**
     * 总页数
     */
    private Long totalPages;

    /**
     * 总数量
     */
    private Long totalCount;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 头部
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> extras;

    /**
     * 数据
     */
    private List<R> list;

    public static <R> ListPayload<R> build(List<R> list) {
        ListPayload<R> payload = new ListPayload<>();
        payload.current = 0L;
        payload.size = (long) list.size();
        payload.totalPages = 1L;
        payload.totalCount = (long) list.size();
        payload.hasNext = false;
        payload.hasPrevious = false;
        payload.list = list;
        return payload;
    }

    public static <T, R> ListPayload<R> build(List<T> list, Function<T, R> convert) {
        return build(list.stream().map(convert).collect(Collectors.toList()));
    }

    @SuppressWarnings("unchecked")
    public static <T, R> ListPayload<R> build(List<T> list, MixIn<T>... mixIns) {
        return build((List<R>) MultipleMixIn.converts(list, mixIns));
    }

    public static <R> ListPayload<R> build(IPage<R> page) {
        ListPayload<R> payload = new ListPayload<>();
        payload.current = page.getCurrent();
        payload.size = page.getSize();
        payload.totalPages = page.getPages();
        payload.totalCount = page.getTotal();
        payload.hasNext = page.getCurrent() < page.getPages();
        payload.hasPrevious = page.getCurrent() > 1;
        payload.list = page.getRecords();
        return payload;
    }

    public static <T, R> ListPayload<R> build(IPage<T> page, Function<T, R> convert) {
        ListPayload<R> payload = new ListPayload<>();
        payload.current = page.getCurrent();
        payload.size = page.getSize();
        payload.totalPages = page.getPages();
        payload.totalCount = page.getTotal();
        payload.hasNext = page.getCurrent() < page.getPages();
        payload.hasPrevious = page.getCurrent() > 1;
        payload.list = page.getRecords().stream().map(convert).collect(Collectors.toList());
        return payload;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> ListPayload<R> build(IPage<T> page, MixIn<T>... mixIns) {
        ListPayload<R> payload = new ListPayload<>();
        payload.current = page.getCurrent();
        payload.size = page.getSize();
        payload.totalPages = page.getPages();
        payload.totalCount = page.getTotal();
        payload.hasNext = page.getCurrent() < page.getPages();
        payload.hasPrevious = page.getCurrent() > 1;
        payload.list = (List<R>) MultipleMixIn.converts(page.getRecords(), mixIns);
        return payload;
    }

    public void putExtras(String key, Object value) {
        if (this.extras == null) {
            this.extras = new HashMap<>();
        }
        this.extras.put(key, value);
    }

}
