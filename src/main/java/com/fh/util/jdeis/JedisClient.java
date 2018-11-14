package com.fh.util.jdeis;

import org.springframework.stereotype.Service;

@Service
public interface JedisClient {
    String get(String key);

    String set(String key, String value);//向redis写入数据

    String hget(String key, String value);//获取储存结构是HSAHMAP

    long hset(String hkey, String key, String value);

    long incr(String key);

    long expire(String key, int second);

    long ttl(String key);

    long del(String key);

    long hdel(String hkey, String key);
}
