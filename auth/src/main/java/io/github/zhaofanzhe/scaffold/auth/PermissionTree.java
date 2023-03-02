package io.github.zhaofanzhe.scaffold.auth;

import java.util.ArrayList;

public class PermissionTree extends ArrayList<PermissionGroup> {

    public final PermissionGroup group(String name, String remark) {
        final PermissionGroup group = new PermissionGroup(name, remark, this::search);
        add(group);
        return group;
    }

    private boolean search(String code) {
        for (PermissionGroup group : this) {
            for (Permission permission : group.getPermissions()) {
                return permission.getCode().equals(code);
            }
        }
        return false;
    }

    public Permission find(String code) {
        for (PermissionGroup group : this) {
            for (Permission permission : group.getPermissions()) {
                if (permission.getCode().equals(code)) {
                    return permission;
                }
            }
        }
        return null;
    }

}
