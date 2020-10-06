package netty.rpc.study.service;

import java.util.Map;

public interface DubboTestService {

    Integer insertUserInfo(Map<String, Object> paramMap);
}
