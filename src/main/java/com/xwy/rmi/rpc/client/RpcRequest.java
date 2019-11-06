package com.xwy.rmi.rpc.client;

import java.io.Serializable;

/**
 *
 * @description: 传输对象
 *
 * @author: xwy
 *
 * @create: 4:25 PM 2019/11/6
**/

public class RpcRequest implements Serializable{


    private static final long serialVersionUID = -8992861624992930865L;
    private String className;
    private String methodName;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}