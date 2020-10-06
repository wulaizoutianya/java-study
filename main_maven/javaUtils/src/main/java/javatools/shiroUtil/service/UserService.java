package javatools.shiroUtil.service;

import javatools.shiroUtil.entity.CmUserBaseInfoEntity;

public interface UserService {

    CmUserBaseInfoEntity findUserByUserAccount(String userAccount);
}
