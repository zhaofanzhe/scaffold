package io.github.zhaofanzhe.scaffold.toolkit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.github.zhaofanzhe.scaffold.mixin.MixIn;
import io.github.zhaofanzhe.scaffold.mixin.MultipleMixIn;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("DuplicatedCode")
@Getter
public class Result {

    public static String SUCCESS = "SUCCESS";
    public static String FAIL = "FAIL";
    public static String PARAM_ERROR = "PARAM_ERROR";
    public static String REQUEST_METHOD_NOT_SUPPORTED = "REQUEST_METHOD_NOT_SUPPORTED";
    public static String UN_CATCH_ERROR = "UN_CATCH_ERROR";

    public static String AUTH_INVALID = "AUTH_INVALID"; // 授权无效(未登录)

    public static String IDENTITY_INVALID = "IDENTITY_INVALID"; // 身份无效(不具备团长/供应商/客服/推广员身份)
    public static String NO_PERMISSION = "NO_PERMISSION"; // 无权限(没有操作权限)
    public static String REQUEST_LIMIT = "REQUEST_LIMIT"; // 请求过于频繁,请稍后再试!

    private String code;

    @JsonInclude(Include.NON_NULL)
    private String message;

    @JsonInclude(Include.NON_NULL)
    private Object payload;

    @JsonInclude(Include.NON_NULL)
    private Object error;

    public static Result build(String code) {
        return new Result().code(code);
    }

    public static Result build(String code, String message) {
        return build(code).message(message);
    }

    public static <T> Result build(String code, String message, T payload) {
        return build(code, message).payload(payload);
    }

    @SafeVarargs
    public static <T> Result build(String code, String message, T payload, MixIn<T>... mixIns) {
        return build(code, message).payload(payload, mixIns);
    }

    public static Result success() {
        return build(SUCCESS);
    }

    public static Result success(String message) {
        return success().message(message);
    }

    public static <T> Result success(String message, T payload) {
        return success().message(message).payload(payload);
    }

    @SafeVarargs
    public static <T> Result success(String message, T payload, MixIn<T>... mixIns) {
        return success().message(message).payload(payload, mixIns);
    }

    public static Result fail() {
        return new Result().code(FAIL);
    }

    public static Result fail(String code) {
        return build(code);
    }

    public static Result error(String code, Object error) {
        return build(code).error(error);
    }

    public static Result failParamError(String message) {
        return fail(PARAM_ERROR).message(message);
    }

    private Result() {
    }

    public Result code(String code) {
        this.code = code;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public <T> Result payload(T payload) {
        this.payload = payload;
        return this;
    }

    @SafeVarargs
    public final <T> Result payload(T payload, MixIn<T>... mixIns) {
        this.payload = MultipleMixIn.convert(payload, mixIns);
        return this;
    }

    @SuppressWarnings({"unchecked"})
    public Result put(String key, Object value) {
        if (this.payload == null) {
            this.payload = new HashMap<String, Object>();
        }
        if (this.payload instanceof Map) {
            ((Map<String, Object>) payload).put(key, value);
        } else if (this.payload instanceof ListPayload) {
            ((ListPayload<?>) payload).putExtras(key, value);
        } else {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        return this;
    }

    public <T> Result list(List<T> list) {
        if (this.payload != null) {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        this.payload = ListPayload.build(list);
        return this;
    }

    public <T, R> Result list(List<T> list, Function<T, R> convert) {
        if (this.payload != null) {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        this.payload = ListPayload.build(list, convert);
        return this;
    }

    @SafeVarargs
    public final <T> Result list(List<T> list, MixIn<T>... mixIns) {
        if (this.payload != null) {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        this.payload = ListPayload.build(list, mixIns);
        return this;
    }

    public <T> Result list(IPage<T> list) {
        if (this.payload != null) {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        this.payload = ListPayload.build(list);
        return this;
    }

    public <T, R> Result list(IPage<T> list, Function<T, R> convert) {
        if (this.payload != null) {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        this.payload = ListPayload.build(list, convert);
        return this;
    }

    @SafeVarargs
    public final <T> Result list(IPage<T> list, MixIn<T>... mixIns) {
        if (this.payload != null) {
            throw new RuntimeException("payload已经有值,不能进行put操作");
        }
        this.payload = ListPayload.build(list, mixIns);
        return this;
    }

    public <T> Result list(LambdaQueryChainWrapper<T> wrapper, boolean optAll) {
        if (optAll) {
            Paging pagingParam = Paging.current();
            if (pagingParam.getPage() == -1 && pagingParam.getSize() == -1) {
                return this.list(wrapper.list());
            }
        }
        return this.list(wrapper.page(Paging.ofPage()));
    }

    public <T, R> Result list(LambdaQueryChainWrapper<T> wrapper, boolean optAll, Function<T, R> convert) {
        if (optAll) {
            Paging pagingParam = Paging.current();
            if (pagingParam.getPage() == -1 && pagingParam.getSize() == -1) {
                return this.list(wrapper.list(), convert);
            }
        }
        return this.list(wrapper.page(Paging.ofPage()), convert);
    }

    @SafeVarargs
    public final <T> Result list(LambdaQueryChainWrapper<T> wrapper, boolean optAll, MixIn<T>... mixIns) {
        if (optAll) {
            Paging pagingParam = Paging.current();
            if (pagingParam.getPage() == -1 && pagingParam.getSize() == -1) {
                return this.list(wrapper.list(), mixIns);
            }
        }
        return this.list(wrapper.page(Paging.ofPage()), mixIns);
    }

    public <T> Result list(LambdaQueryChainWrapper<T> wrapper) {
        return this.list(wrapper, false);
    }

    public <T, R> Result list(LambdaQueryChainWrapper<T> wrapper, Function<T, R> convert) {
        return this.list(wrapper, false, convert);
    }

    @SafeVarargs
    public final <T> Result list(LambdaQueryChainWrapper<T> wrapper, MixIn<T>... mixIns) {
        return this.list(wrapper, false, mixIns);
    }

    public Result error(Object errors) {
        this.error = errors;
        return this;
    }

}
