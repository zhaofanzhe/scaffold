package io.github.zhaofanzhe.scaffold.auth;

public class Permission {
    private final String group;

    private final String code;

    private final String remark;

    Permission(String group, String code, String remark) {
        this.group = group;
        this.code = code;
        this.remark = remark;
    }

    public final String getGroup() {
        return group;
    }

    public final String getCode() {
        return code;
    }

    public final String getRemark() {
        return remark;
    }

    @Override
    public boolean equals(Object permission) {
        if (permission == null) return false;
        if (!(permission instanceof Permission)) return false;
        if (code == null && ((Permission) permission).code == null) return true;
        if (code == null) return false;
        return code.equals(((Permission) permission).code);
    }
}
