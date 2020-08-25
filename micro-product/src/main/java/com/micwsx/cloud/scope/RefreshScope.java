package com.micwsx.cloud.scope;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael
 * @create 8/20/2020 3:51 PM
 */
public class RefreshScope implements Scope {

    public static final String SCOPE_NAME="refresh";

    private final ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

    // 自定义获取bean过程
    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        // 缓存中已存在
        if (concurrentHashMap.containsKey(s)) {
            return concurrentHashMap.get(s);
        }
        Object bean = objectFactory.getObject();
        concurrentHashMap.put(s, bean);
        return bean;
    }

    @Override
    public Object remove(String s) {
        return concurrentHashMap.remove(s);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
