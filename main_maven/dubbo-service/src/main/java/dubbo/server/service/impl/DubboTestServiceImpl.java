package dubbo.server.service.impl;

import dubbo.server.dao.CmUserBaseInfoDao;
import netty.rpc.study.service.DubboTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(value = "dubboTestService")
public class DubboTestServiceImpl implements DubboTestService {

    @Autowired
    CmUserBaseInfoDao cmUserBaseInfoDao;

    @Autowired
    TestRollBackServiceImpl testRollBackService;

    @Override
    public Integer insertUserInfo(Map<String, Object> paramMap) {
        try {
            int i = testRollBackService.testTrans();
            System.out.println(i);
        } catch (Exception e) {
            System.out.println("-----::::" + e.getCause());
            e.printStackTrace();
        }
        return null;
    }


}
