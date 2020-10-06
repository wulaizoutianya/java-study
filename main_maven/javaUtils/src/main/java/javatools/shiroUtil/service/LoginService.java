package javatools.shiroUtil.service;

import javatools.shiroUtil.entity.CmSysRoleInfoEntity;
import javatools.shiroUtil.entity.CmUserBaseInfoEntity;
import javatools.shiroUtil.entity.LoginResult;

public interface LoginService {

    CmUserBaseInfoEntity addUser(CmUserBaseInfoEntity user);

    CmSysRoleInfoEntity addRole(CmSysRoleInfoEntity role);

    LoginResult userLogin(String userAccount, String password);

    void userLogout();
}
