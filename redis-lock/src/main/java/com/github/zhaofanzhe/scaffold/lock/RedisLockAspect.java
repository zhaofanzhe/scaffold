package com.github.zhaofanzhe.scaffold.lock;

import cn.hutool.core.util.StrUtil;
import com.github.zhaofanzhe.scaffold.auth.AuthCore;
import com.github.zhaofanzhe.scaffold.auth.AuthService;
import com.github.zhaofanzhe.scaffold.auth.DefaultAuth;
import com.github.zhaofanzhe.scaffold.toolkit.Result;
import com.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedisLockAspect {

    private final RedisLockRegistry redisLockRegistry;

    @Around(value = "@annotation(redisLock)")
    public Object redisLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {

        final String lockKey = getRedisKey(joinPoint, redisLock);

        Lock lock = redisLockRegistry.obtain(lockKey);

        boolean isLock = false;

        try {

            log.info("开始锁定 [{}]", lockKey);

            isLock = lock.tryLock(redisLock.waitLockTime(), TimeUnit.SECONDS);

            if (!isLock) {
                log.info("锁定失败 [{}]", lockKey);
                return Result.fail(Result.REQUEST_LIMIT).message("请求超出频率限制");
            }

            log.info("锁定成功 [{}]", lockKey);

            return joinPoint.proceed();
        } finally {
            if (isLock) {
                lock.unlock();
                log.info("解锁成功 [{}]", lockKey);
            }
        }

    }

    private String getMethodKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        String key = redisLock.key();
        if (StrUtil.isNotEmpty(key)) {
            return key;
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return String.format("%s>%s", method.getDeclaringClass().getName(), method.getName());
    }

    private String getRedisKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        switch (redisLock.type()) {
            case System -> {
                return getSystemKey(joinPoint, redisLock);
            }
            case User -> {
                return getUserKey(joinPoint, redisLock);
            }
            case SpEL -> {
                return getSpELKey(joinPoint, redisLock);
            }
            default -> throw new RuntimeException("枚举值错误");
        }
    }


    private String getSystemKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        return getMethodKey(joinPoint, redisLock);
    }

    private String getUserKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        // noinspection unchecked
        final AuthCore<?> auth = Objects.requireNonNull(SpringContextHolder.getBean(AuthService.class)).load(DefaultAuth.class);
        return String.format("%s:%s:%s", getMethodKey(joinPoint, redisLock), auth.getGenre(), auth.getId());
    }

    ExpressionParser parser = new SpelExpressionParser();

    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    private String getSpELKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        final Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] params = discoverer.getParameterNames(method);

        EvaluationContext context = new StandardEvaluationContext();

        if (params != null) {
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], args[len]);
            }
        }

        Expression keyExpression = parser.parseExpression(redisLock.key());

        return keyExpression.getValue(context, String.class);
    }

}
