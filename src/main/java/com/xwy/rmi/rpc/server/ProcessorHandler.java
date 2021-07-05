package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.client.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 3:55 PM 2020/8/31
**/

public class ProcessorHandler implements Runnable {

    private Socket socket;
//    private Object service;//发布的服务

    Map<String, Object> handlerMap;

    public ProcessorHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        //处理请求

        ObjectInputStream inputStream = null;

        try {
            // 获取客户端的输入流
            inputStream = new ObjectInputStream(socket.getInputStream());

            // 反序列化远程传输对象RpcRequest
            RpcRequest request = (RpcRequest) inputStream.readObject();
            Object result = this.invoke(request);

            // 通过输出流将结果输出给客户端
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

            inputStream.close();
            objectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest request) throws Exception {
        //以下均为反射操作，目的是通过反射调用服务
        Object[] args = request.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }

        String serviceName = request.getClassName();
        String version = request.getVersion();
        if (version != null && !"".equals(version)) {
            serviceName = serviceName + "-" + version;
        }
        // 从handlerMap中，根据客户端请求的地址去拿到响应的服务，通过反射发起调用
        Object service = handlerMap.get(serviceName);
        Method method = service.getClass().getMethod(request.getMethodName(), types);
        return method.invoke(service, args);
    }
}