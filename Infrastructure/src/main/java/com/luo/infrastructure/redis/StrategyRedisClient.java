package com.luo.infrastructure.redis;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class StrategyRedisClient<T> implements IRedisClient<T>{


    @Resource
    private RedissonClient redissonClient;

    @Override
    public void setValue(String key, T value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public  RBucket<T> getValue(String key) {
        return redissonClient.getBucket(key);
    }

    @Override
    public RAtomicLong getAtomicLong(String key) {
       return redissonClient.getAtomicLong(key);
    }


}
