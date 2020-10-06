package javatools.shiroUtil.dao;

import javatools.shiroUtil.entity.CmUserBaseInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CmUserBaseInfoDao {

    CmUserBaseInfoEntity findUserByUserAccount(String userAccount);

    String userLoginCheck(Map<String, String> paramMap);

    List<Map<String, Object>> queryCmSysPowerMenu();

    List<Map<String, Object>> queryUserOrganInfo(String organId);

    List<Map<String, Object>> queryByMapUserOrganInfo(Map<String, Object> paramMap);


}
