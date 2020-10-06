package dubbo.server.service.impl;

import dubbo.server.dao.CmUserBaseInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(value = "testRollBackService")
public class TestRollBackServiceImpl {

    @Autowired
    CmUserBaseInfoDao cmUserBaseInfoDao;

    @Transactional(rollbackFor = Exception.class)
    public int testTrans() {
        Map<String, Object> paramMap1 = new HashMap<>();
        paramMap1.put("userEnName", "bbbb");
        paramMap1.put("userChName", "子子");
        cmUserBaseInfoDao.insertUserInfo(paramMap1);

        Map<String, Object> paramMap2 = new HashMap<>();
        paramMap2.put("userEnName", "aaaa");
        paramMap2.put("userChName", "工工");
        return cmUserBaseInfoDao.insertUserInfo(paramMap2);
    }
}
