package com.xwy.rmi.rpc.server;

import com.xwy.rmi.rpc.anno.RpcAnnotation;
import com.xwy.rmi.rpc.zk.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 3:35 PM 2020/8/31
**/

public class RpcServer {

    //创建一个线程池
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private IRegisterCenter registerCenter;//注册中心
    private String serviceAddress;//服务发布地址

    // 服务名称和服务对象之间的关系
    Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    /**
     * 绑定服务名称和服务对象
     *
     * @param services
     */
    public void bind(Object... services) {
        for (Object service : services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();
            String version = annotation.version();
            if (version != null && !version.equals("")) {
                serviceName = serviceName + "-" + version;
            }
            handlerMap.put(serviceName, service);//绑定服务接口名称对应的服务
        }
    }

//    public void publisher(final Object service, int port) {
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(port);
//
//            while (true) {
//                Socket socket = serverSocket.accept();
//                executorService.execute(new ProcessorHandler(socket, service));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (serverSocket != null) {
//                try {
//                    serverSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    public void publisher() {
        ServerSocket serverSocket = null;
        try {
            String[] addrs = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.valueOf(addrs[1]));//启动一个服务监听

            for (String interfaceName : handlerMap.keySet()) {
                registerCenter.register(interfaceName, serviceAddress);
                System.out.println("注册服务成功：" + interfaceName + "->" + serviceAddress);
            }

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}