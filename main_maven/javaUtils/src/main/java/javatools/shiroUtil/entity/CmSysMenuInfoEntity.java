package javatools.shiroUtil.entity;

public class CmSysMenuInfoEntity {

    private Long menuId;  //名称
    private Long parentId;  //父编号
    private String menuName;    //名称
    private String menuType;    //资源类型
    private String menuUrl;     //资源路径
    private String menuPermit;  //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private String belongOrgan; //所属机构

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuPermit() {
        return menuPermit;
    }

    public void setMenuPermit(String menuPermit) {
        this.menuPermit = menuPermit;
    }

    public String getBelongOrgan() {
        return belongOrgan;
    }

    public void setBelongOrgan(String belongOrgan) {
        this.belongOrgan = belongOrgan;
    }
}
