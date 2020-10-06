package javatools.shiroUtil.entity;

import java.util.List;

public class CmSysRoleInfoEntity {

    private Integer roleId; // 编号,角色标识程序中判断使用,如"admin",这个是唯一的:
    private String roleName; // 角色名称
    private String roleDescribe; // 角色描述
    private String roleState; // 是否可用,如果不可用将不会添加给用户
    private List<CmSysMenuInfoEntity> roleMenuList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public String getRoleState() {
        return roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public List<CmSysMenuInfoEntity> getRoleMenuList() {
        return roleMenuList;
    }

    public void setRoleMenuList(List<CmSysMenuInfoEntity> roleMenuList) {
        this.roleMenuList = roleMenuList;
    }
}
