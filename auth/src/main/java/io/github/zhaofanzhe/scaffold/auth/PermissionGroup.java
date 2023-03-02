package io.github.zhaofanzhe.scaffold.auth;

import java.util.ArrayList;
import java.util.List;

public class PermissionGroup {

    private final String group;

    private final String remark;

    private final List<Permission> permissions = new ArrayList<>();

    private final SearchPermission search;

    PermissionGroup(String group, String remark, SearchPermission search) {
        this.group = group;
        this.remark = remark;
        this.search = search;
    }

    public final String getGroup() {
        return group;
    }

    public final String getRemark() {
        return remark;
    }

    public final List<Permission> getPermissions() {
        return permissions;
    }

    public Permission permission(String code, String remark) {
        if (search.search(code)) {
            throw new RuntimeException(String.format("权限码 %s 已存在", code));
        }
        final Permission permission = new Permission(group, code, remark);
        permissions.add(permission);
        return permission;
    }

}
