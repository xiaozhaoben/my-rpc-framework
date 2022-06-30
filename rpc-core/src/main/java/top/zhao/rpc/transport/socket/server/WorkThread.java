package top.zhao.rpc.transport.socket.server;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 实际进行过程调用线程
 *
 *@author xiaozhao
 */
@Slf4j
@AllArgsConstructor
public class WorkThread implements Runnable{


    private Object service;
    private Socket socket;

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            log.info("调用" + service.getClass().getName() + "的" + request.getMethodName() + "方法");
            Object res = method.invoke(service, request.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(res, request.getRequestId()));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
