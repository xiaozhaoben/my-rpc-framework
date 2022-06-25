package top.zhao.rpc.server;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;
import top.zhao.rpc.registry.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 处理request的工作线程
 *
 *@author xiaozhao
 */
@Slf4j
@AllArgsConstructor
public class RequestHandlerThread implements Runnable{


    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            String interfaceName = request.getInterfaceName();
            Object service = serviceRegistry.getRegistry(interfaceName);
            Object res = requestHandler.handle(request, service);
            objectOutputStream.writeObject(RpcResponse.success(res));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用时发生错误", e);
        }
    }
}
