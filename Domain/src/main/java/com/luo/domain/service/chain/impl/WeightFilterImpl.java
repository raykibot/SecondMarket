package com.luo.domain.service.chain.impl;

import com.luo.domain.entity.vo.ChainResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.chain.AbstractChain;
import com.luo.domain.service.chain.IChainFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NavigableMap;
import java.util.TreeMap;

@Component("rule_weight")
@Slf4j
public class WeightFilterImpl extends AbstractChain {


    @Autowired
    private IStrategyRepository repository;

    private Integer userWeight = 0;

    @Override
    public ChainResult processLogic(String username, Integer strategyId) {

        log.info("责任链过滤---权重过滤节点");

        if (userWeight >= 4000) {
            log.info("责任链过滤---权重节点接管");
            //判断权重区间
            NavigableMap<Integer, Integer> rangeMap = new TreeMap<>();
            rangeMap.put(4000, 4000);
            rangeMap.put(5000, 5000);
            rangeMap.put(6000, 6000);

// 查找小于等于 weight 的最大键值
            userWeight = rangeMap.floorEntry(userWeight).getValue();
            Integer weightAward = repository.getWeightAward(strategyId, userWeight);
            return ChainResult.builder().awardId(weightAward).awardDesc("权重抽奖返回").build();
        }
        log.info("责任链规则---权重节点放行");
        return next().logic(username,strategyId);
    }
}
