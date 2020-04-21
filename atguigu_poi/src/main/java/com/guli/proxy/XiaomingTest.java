package com.guli.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * 手写代理模式测试
 */
public class XiaomingTest {

    public static void main(String[] args) throws Exception{
        //new Xiaoming().findLove();
        Person person = (Person) new Meipo().proxyInterface(new Xiaoming());
        System.err.println(person.getClass());
        person.findLove();

        //原理：
        //1、拿到被代理者对象的引用，获取被代理者的所有接口
        //2、JDK帮我们重新生成一个类、并且帮我们实现被代理者接口的实现
        //3、重新动态生成一个class字节码
        //4、重新编译
        byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
        FileOutputStream outputStream = new FileOutputStream("D:/test-Proxy0.class");
        outputStream.write(data);
        outputStream.close();
    }

}
