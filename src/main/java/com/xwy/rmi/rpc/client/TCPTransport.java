package com.xwy.rmi.rpc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @description:
 * @author: xwy
 * @create: 4:25 PM 2020/8/31
 **/

public class TCPTransport {

//    private String host;
//    private int port;
//
//    public TCPTransport(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }

    private String serviceAddress;

    public TCPTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private Socket newSocket() {
        System.out.println("创建一个新的连接");

        Socket socket;
        try {
            String[] arrs = serviceAddress.split(":");
            socket = new Socket(arrs[0], Integer.valueOf(arrs[1]));
            return socket;
        } catch (Exception e) {
            throw new RuntimeException("连接建立失败");
        }

    }

    public Object send(RpcRequest request) {
        Socket socket = null;

        try {
            socket = newSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object result = objectInputStream.readObject();

            objectOutputStream.close();
            objectInputStream.close();

            return result;
        } catch (Exception e) {
            throw new RuntimeException("发起远程调用异常：" + e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}