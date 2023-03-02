package io.github.zhaofanzhe.scaffold.auth;

import cn.hutool.core.util.StrUtil;
import io.github.zhaofanzhe.scaffold.exception.BusinessInterruptionException;
import io.github.zhaofanzhe.scaffold.toolkit.Result;
import io.github.zhaofanzhe.scaffold.toolkit.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
@SuppressWarnings("unchecked")
public abstract class BaseAuth<BA extends BaseAuth<BA>> implements AuthCore<BA> {

    private String sessionId;

    private String genre;

    private String source;

    private Integer id;

    private List<String> permissions;

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getGenre() {
        return this.genre;
    }

    @Override
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public List<String> getPermissions() {
        return this.permissions;
    }

    @Override
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean isGenre(String genre) {
        return StrUtil.equals(this.genre, genre);
    }

    @Override
    public boolean isSource(String source) {
        return StrUtil.equals(this.source, source);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        if (permission == null) return true;
        if (this.permissions == null || this.permissions.size() == 0) return false;
        return permissions.contains(permission.getCode());
    }

    public void requiredPermission(Permission permission) {
        if (!hasPermission(permission))
            throw new BusinessInterruptionException(Result.fail(Result.NO_PERMISSION).message("无操作权限"));
    }

    @Override
    public BA login(String genre, String source, Integer id) {
        this.genre = genre;
        this.source = source;
        this.id = id;
        save();
        return (BA) this;
    }

    @Override
    public BA login(String genre, String source, Integer id, List<Permission> permissions) {
        this.genre = genre;
        this.source = source;
        this.id = id;
        this.permissions = permissions.stream().map(Permission::getCode).toList();
        save();
        return (BA) this;
    }

    @Override
    public BA logout() {
        this.genre = null;
        this.source = null;
        this.id = null;
        this.permissions = null;
        save();
        return (BA) this;
    }

    @Override
    public BA logoutOthers() {
        for (BA auth : onlineAuths()) {
            if (auth.getSessionId().equals(sessionId)) {
                continue;
            }
            if (!auth.getSource().equals(source)) {
                continue;
            }
            log.info("{} 登出", auth.getSessionId());
            auth.logout();
        }
        return (BA) this;
    }

    @Override
    public List<BA> onlineAuths(String genre, Integer id) {
        return Objects.requireNonNull(SpringContextHolder.getBean(AuthService.class)).onlineAuths(this, genre, id);
    }

    @Override
    public List<BA> onlineAuths() {
        return Objects.requireNonNull(SpringContextHolder.getBean(AuthService.class)).onlineAuths(this);
    }

    @Override
    public void save() {
        Objects.requireNonNull(SpringContextHolder.getBean(AuthService.class)).save(this);
    }

}
