package javatools.shiroUtil.service.impl;

import javatools.shiroUtil.entity.CmSysMenuInfoEntity;
import javatools.shiroUtil.entity.CmSysRoleInfoEntity;
import javatools.shiroUtil.entity.CmUserBaseInfoEntity;
import javatools.shiroUtil.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public CmUserBaseInfoEntity findUserByUserAccount(String userAccount) {
        CmUserBaseInfoEntity cmUserBaseInfoEntity = new CmUserBaseInfoEntity();
        cmUserBaseInfoEntity.setUserAccount("string");
        cmUserBaseInfoEntity.setUserPwd("string");
        List<CmSysRoleInfoEntity> userRoleList = new ArrayList<>();
        CmSysRoleInfoEntity role = new CmSysRoleInfoEntity();
        role.setRoleId(1);
        role.setRoleName("admin");
        List<CmSysMenuInfoEntity> meunList = new ArrayList<>();
        CmSysMenuInfoEntity menuInfoEntity = new CmSysMenuInfoEntity();
        menuInfoEntity.setMenuPermit("create");
        meunList.add(menuInfoEntity);
        role.setRoleMenuList(meunList);
        userRoleList.add(role);
        cmUserBaseInfoEntity.setUserRoleList(userRoleList);

        return cmUserBaseInfoEntity;
    }

}
