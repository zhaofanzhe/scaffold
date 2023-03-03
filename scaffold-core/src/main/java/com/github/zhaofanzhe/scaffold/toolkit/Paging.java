package com.github.zhaofanzhe.scaffold.toolkit;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.zhaofanzhe.scaffold.exception.BusinessInterruptionException;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("DuplicatedCode")
public class Paging {

    private final int page;

    private final int size;

    private Paging(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public static Paging current() {

        final HttpServletRequest request = SpringContextHolder.getRequest();

        String page = request.getParameter("page");

        String size = request.getParameter("size");

        int pageNumber = 0;

        int sizeNumber = 20;

        if (!StrUtil.isEmpty(page)) {
            try {
                pageNumber = Integer.parseInt(page);
            } catch (NumberFormatException e) {
                throw new BusinessInterruptionException(
                        Result.error(Result.PARAM_ERROR, MapUtil.of(new String[][]{
                                {"page", "page 格式异常"}
                        }))
                );
            }
        }

        if (!StrUtil.isEmpty(size)) {
            try {
                sizeNumber = Integer.parseInt(size);
            } catch (NumberFormatException e) {
                throw new BusinessInterruptionException(
                        Result.error(Result.PARAM_ERROR, MapUtil.of(new String[][]{
                                {"size", "size 格式异常"}
                        }))
                );
            }
        }

        return new Paging(pageNumber, sizeNumber);
    }

    public static <T> Page<T> ofPage() {
        return ofPage(false);
    }

    public static <T> Page<T> ofPage(boolean optAll) {
        Paging paging = Paging.current();

        if (optAll && paging.page < 0 && paging.size < 0) {
            return Page.of(1, -1);
        }

        if (paging.page < 0) {
            throw new BusinessInterruptionException(
                    Result.error(Result.PARAM_ERROR, MapUtil.of(new String[][]{
                            {"page", "page 需大于等于0"}
                    }))
            );
        }

        if (paging.size < 0) {
            throw new BusinessInterruptionException(
                    Result.error(Result.PARAM_ERROR, MapUtil.of(new String[][]{
                            {"size", "size 需大于等于0"}
                    }))
            );
        }

        return Page.of(paging.page + 1, paging.size);
    }

}
