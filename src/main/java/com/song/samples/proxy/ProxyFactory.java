package com.song.samples.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * dynamic proxy
 */
public class ProxyFactory {
    // target object
    private IUserDao target;

    public ProxyFactory(IUserDao target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        // proxy object
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("start transaction2 ...");
            // execute target class method
            Object returnValue = method.invoke(target, args);
            System.out.println("end transaction2 ..");
            return returnValue;
        });
    }

    public static void main(String[] args) {
        UserDao target = new UserDao();
        System.out.println(target.getClass());
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        proxy.save();
    }
}
