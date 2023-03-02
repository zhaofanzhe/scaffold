package com.github.zhaofanzhe.scaffold.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import cn.hutool.core.util.ReflectUtil;
import com.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Service
@RequiredArgsConstructor
public class AuthService<S extends Session> {

    private final FindByIndexNameSessionRepository<S> sessionRepository;

    private List<String> getFields(Class<?> clazz) {
        return BeanUtil.getBeanDesc(clazz).getProps().stream().map(PropDesc::getFieldName).toList();
    }

    private <A extends AuthCore<A>> A load(Class<A> clazz, SessionOperator operator) {
        final A auth = ReflectUtil.newInstanceIfPossible(clazz);

        for (String field : getFields(clazz)) {
            Object attribute = operator.getAttribute(authKey(field));
            if (attribute == null) continue;
            BeanUtil.setFieldValue(auth, field, attribute);
        }

        auth.setSessionId(operator.getId());

        return auth;
    }

    public <A extends AuthCore<A>> A load(Class<A> clazz, String sessionId) {

        SessionOperator operator;

        final HttpSession session = SpringContextHolder.getSession();

        if (session.getId().equals(sessionId)) {
            operator = new HttpSessionOperator(session);
        } else {
            operator = new RepositorySessionOperator<>(sessionRepository.findById(sessionId));
        }

        if (!operator.getId().equals(sessionId)) {
            throw new RuntimeException("sessionId不一致");
        }

        return load(clazz, operator);
    }

    public <A extends AuthCore<A>> A load(Class<A> clazz) {
        final RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        return load(clazz, attributes.getSessionId());
    }

    public <A extends AuthCore<A>> void save(A auth) {
        SessionOperator operator;

        final HttpSession session = SpringContextHolder.getSession();

        if (session.getId().equals(auth.getSessionId())) {
            operator = new HttpSessionOperator(session);
        } else {
            operator = new RepositorySessionOperator<>(sessionRepository.findById(auth.getSessionId()));
        }

        if (!operator.getId().equals(auth.getSessionId())) {
            throw new RuntimeException("sessionId不一致");
        }

        save(auth, operator);
    }

    private <A extends AuthCore<A>> void save(A auth, SessionOperator operator) {

        for (String field : getFields(auth.getClass())) {
            final Object value = BeanUtil.getFieldValue(auth, field);
            operator.setAttribute(authKey(field), value);
        }

        if (auth.getId() != null) {
            operator.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, authIndex(auth));
        } else {
            operator.removeAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        }

        if (operator instanceof RepositorySessionOperator<?> op) {
            sessionRepository.save((S) op.getSession());
        }

    }

    public <A extends AuthCore<A>> List<A> onlineAuths(A auth, String genre, Integer id) {
        final List<A> onlineAuths = new ArrayList<>();
        final String index = authIndex(genre, id);
        final Map<String, S> sessions = sessionRepository.findByPrincipalName(index);
        for (String key : sessions.keySet()) {
            final S session = sessions.get(key);
            onlineAuths.add((A) load(auth.getClass(), new RepositorySessionOperator<>(session)));
        }
        return onlineAuths;
    }

    public <A extends AuthCore<A>> List<A> onlineAuths(A auth) {
        final List<A> onlineAuths = new ArrayList<>();
        final String index = authIndex(auth);
        final Map<String, S> sessions = sessionRepository.findByPrincipalName(index);
        for (String key : sessions.keySet()) {
            final S session = sessions.get(key);
            onlineAuths.add((A) load(auth.getClass(), new RepositorySessionOperator<>(session)));
        }
        return onlineAuths;
    }

    private String authKey(String key) {
        return String.format("auth:%s", key);
    }

    private <A extends AuthCore<A>> String authIndex(A auth) {
        return authIndex(auth.getGenre(), auth.getId());
    }

    private <A extends AuthCore<A>> String authIndex(String genre, Integer id) {
        return String.format("%s:%d", genre, id);
    }

}
