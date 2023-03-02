package io.github.zhaofanzhe.scaffold.auth;

import org.springframework.session.Session;

@SuppressWarnings("ClassCanBeRecord")
public class RepositorySessionOperator<S extends Session> implements SessionOperator {

    private final S session;

    public RepositorySessionOperator(S session) {
        this.session = session;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public <T> T getAttribute(String key) {
        return session.getAttribute(key);
    }

    @Override
    public <T> void setAttribute(String key, T value) {
        session.setAttribute(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        session.removeAttribute(key);
    }

    public S getSession() {
        return session;
    }
}
