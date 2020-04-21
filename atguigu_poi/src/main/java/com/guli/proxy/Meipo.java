package com.guli.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Meipo implements InvocationHandler {

    private Person target;

    public Object proxyInterface(Person person) throws Exception{
        this.target = person;
        Class  clazz = this.target.getClass();
        System.err.println(clazz);
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("这是媒婆给你做的事情开始：");
        System.err.println("给你找个异性的---");
        target.findLove();
        System.err.println("媒婆给你做的事情结束；");
        return null;
    }
}
