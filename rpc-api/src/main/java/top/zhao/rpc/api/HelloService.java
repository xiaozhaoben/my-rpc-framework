package top.zhao.rpc.api;

import top.zhao.rpc.entity.HelloEntity;

/**
 * 测试接口
 *@author xiaozhao
 */
public interface HelloService {

    /**
     * 设置服务接口方法
     */
    String hello(HelloEntity helloEntity);
}
