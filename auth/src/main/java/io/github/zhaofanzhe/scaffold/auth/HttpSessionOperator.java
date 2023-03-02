package io.github.zhaofanzhe.scaffold.auth;

import javax.servlet.http.HttpSession;

public class HttpSessionOperator implements SessionOperator {

    private final HttpSession session;

    public HttpSessionOperator(HttpSession session) {
        this.session = session;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public <T> T getAttribute(String key) {
        //noinspection unchecked
        return (T) session.getAttribute(key);
    }

    @Override
    public <T> void setAttribute(String key, T value) {
        session.setAttribute(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        session.removeAttribute(key);
    }
}
