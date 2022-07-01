package top.zhao.rpc.test;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.annotation.Service;
import top.zhao.rpc.api.HelloEntity;
import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.api.SayService;

/**
 * 测试服务接口实现类
 *
 *@author xiaozhao
 */
@Slf4j
@Service
public class SayServiceImpl implements SayService {


    @Override
    public String say(String msg) {
        log.info("接受信息为{}", msg);
        return "say bye" + msg;
    }
}
