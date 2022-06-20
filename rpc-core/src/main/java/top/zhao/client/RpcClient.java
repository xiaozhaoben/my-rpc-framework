package top.zhao.client;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * rpc客户端
 *
 * @author :xiaozhao
 */
@Slf4j
public class RpcClient {

    public Object sendRequest(RpcRequest request, String host, Integer port){
        try(Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("rpc客户端调用发生错误:", e);
            return null;
        }
    }
}
