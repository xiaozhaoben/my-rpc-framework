package top.zhao.rpc.transport.socket.server;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;
import top.zhao.rpc.enums.ResponseCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 进行调用的处理器
 *
 *@author xiaozhao
 */
@Slf4j
public class RequestHandler {

    public Object handle(RpcRequest request, Object service){
        Object res = null;
        try {
            res = invokeMethod(request, service);
            log.info("服务 :{}成功调用方法:{}", request.getInterfaceName(), request.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("服务调用时发生错误", e);
        }
        return res;
    }

    public Object invokeMethod(RpcRequest request, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
        } catch (NoSuchMethodException e) {
            log.error("方法未找到", e);
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, request.getRequestId());
        }
        return method.invoke(service, request.getParameters());
    }

}
