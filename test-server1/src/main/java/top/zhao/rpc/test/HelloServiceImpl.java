package top.zhao.rpc.test;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.annotation.Service;
import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.api.HelloEntity;

/**
 * 测试服务接口实现类
 *
 *@author xiaozhao
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(HelloEntity helloEntity) {
        log.info("接受消息为: {}", helloEntity.getMsg());
        return helloEntity.getMsg() + "真不错";
    }
}
