package com.test.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * desc   : ListenerInvocationHandler
 * 代理的实现
 * AOP切面思想
 */
public class ListenerInvocationHandler implements InvocationHandler {
    //拦截的对象,可能是Activity可能是Fragment
    //这里是MainActivity的OnClick方法
    private Object target;
    //存储方法名和方法
    //这里key:OnClick value : 预期回调的目标方法 clickEvent
    private HashMap<String,Method> methodHashMap=new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target!=null) {
            String name = method.getName();
            method=methodHashMap.get(name);//集合中有需要拦截的方法名直接拦截替换
            if (method!=null) {
                return method.invoke(target,args);
            }
        }
        return null;
    }

    /**
     * @param methodName 要拦截的方法名methodName:OnClick
     * @param method 需要替换执行的方法method:clickEvent
     * 添加要拦截的方法
     */
    public void addMethod(String methodName,Method method){
        methodHashMap.put(methodName,method);
    }
}
