package com.luo.domain.service;

import com.luo.domain.entity.vo.AwardVO;
import com.luo.domain.repository.IStrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NavigableMap;
import java.util.TreeMap;


@Service
public class RaffleService implements IRaffleService{


    @Autowired
    private IStrategyRepository repository;


    @Override
    public Integer raffle(Integer strategyId) {

        //获取奖品查找表范围
        Integer range = repository.getAwardTableRange(strategyId);

        //随机生成范围内索引  凭借该索引去表中查找对应奖品信息
        return  repository.getAwardWithIndex(range,strategyId);

    }

    @Override
    public Integer raffleWithWeight(Integer strategyId, Integer weight) {

        //判断权重区间
        NavigableMap<Integer, Integer> rangeMap = new TreeMap<>();
        rangeMap.put(4000, 4000);
        rangeMap.put(5000, 5000);
        rangeMap.put(6000, 6000);

// 查找小于等于 weight 的最大键值
        weight = rangeMap.floorEntry(weight).getValue();

        return repository.getWeightAward(strategyId,weight);

    }
}
