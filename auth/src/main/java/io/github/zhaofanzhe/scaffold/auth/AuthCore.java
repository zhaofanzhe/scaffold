package io.github.zhaofanzhe.scaffold.auth;

import java.util.List;

public interface AuthCore<S extends AuthCore<S>> {

    public String getSessionId();

    public void setSessionId(String sessionId);

    public String getGenre();

    public void setGenre(String genre);

    public String getSource();

    public void setSource(String source);

    public Integer getId();

    public void setId(Integer id);

    public List<String> getPermissions();

    public void setPermissions(List<String> permissions);

    public boolean isGenre(String genre);

    public boolean isSource(String source);

    public boolean hasPermission(Permission permission);

    public void requiredPermission(Permission permission);

    public S login(String genre, String source, Integer id);

    public S login(String genre, String source, Integer id, List<Permission> permissions);

    public S logout();

    public S logoutOthers();

    public List<S> onlineAuths(String genre, Integer id);

    public List<S> onlineAuths();

    public void save();

}
