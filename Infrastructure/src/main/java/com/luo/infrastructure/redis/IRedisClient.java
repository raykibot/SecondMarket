package com.luo.infrastructure.redis;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;

public interface IRedisClient<T> {


    /**
     * 设置缓存
     * @param key
     * @param value
     */
     void setValue (String key,T value);


    /**
     * 获取缓存
     * @param key
     * @return RBucket
     */
    RBucket<T> getValue (String key);


    RAtomicLong getAtomicLong(String key);






}
