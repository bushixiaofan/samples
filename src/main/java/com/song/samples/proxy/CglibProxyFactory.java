package com.song.samples.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib proxy
 */
public class CglibProxyFactory implements MethodInterceptor {
    // target object
    private Object target;

    public CglibProxyFactory(Object target) {
        this.target = target;
    }

    // proxy object
    public Object getProxyInstance() {
        // tool class
        Enhancer enhancer = new Enhancer();
        // super class
        enhancer.setSuperclass(target.getClass());
        // set callback
        enhancer.setCallback(this);
        // create child class (proxy)
        return enhancer.create();

    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("begin transaction3 ...");
        // execute target class method
        Object returnValue = method.invoke(target, objects);
        System.out.println("end transaction3 ...");
        return returnValue;
    }

    public static void main(String[] args) {
        UserDao target = new UserDao();
        UserDao proxy = (UserDao) new CglibProxyFactory(target).getProxyInstance();
        proxy.save();
    }
}
