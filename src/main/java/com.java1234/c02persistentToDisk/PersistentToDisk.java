package com.java1234.c02persistentToDisk;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PersistentToDisk {
    public static void main(String[] args) {
        // 根据ehcache.xml配置文件创建Cache管理器
        CacheManager manager = CacheManager.create("./src/main/resources/02persistentToDisk.xml");
        // 获取指定cache
        Cache c = manager.getCache("a");
        // 创建元素
        Element e = new Element("java1234", "牛逼");
        // 把一个元素添加到cache中
        c.put(e);
        // 根据key从cache中获取缓存元素
        Element e2 = c.get("java1234");
        System.out.println(e2);
        System.out.println(e2.getObjectKey());
        System.out.println(e2.getObjectValue());
        // 刷新缓存，即把cache中的数据放到内存缓存中
        c.flush();
        // 关闭Cache管理器
        manager.shutdown();
    }
}
