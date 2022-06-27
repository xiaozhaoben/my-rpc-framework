package top.zhao.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.enums.SerializerCode;
import top.zhao.rpc.exception.SerializeException;

import java.io.IOException;

/**
 * 使用JSON格式的序列化器
 *
 * @author xiaozhao
 */
@Slf4j
public class JsonSerializer implements CommonSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化时有错误发生: {}", e.getMessage());
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            if(obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            log.error("反序列化时有错误发生: {}", e.getMessage());
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    /*
        这里由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类型
        需要重新判断处理
     */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for(int i = 0; i < rpcRequest.getParamTypes().length; i ++) {
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }

}