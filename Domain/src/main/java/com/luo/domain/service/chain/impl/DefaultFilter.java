package com.luo.domain.service.chain.impl;


import com.luo.domain.entity.vo.ChainResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.IRaffleService;
import com.luo.domain.service.chain.AbstractChain;
import com.luo.domain.service.chain.IChainFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rule_lock")
@Slf4j
public class DefaultFilter extends AbstractChain {


    @Autowired
    private IRaffleService raffleService;

    @Override
    public ChainResult processLogic(String username, Integer strategyId) {

        log.info("责任链过滤---默认节点");

        Integer awardId = raffleService.raffle(strategyId);
        return ChainResult.builder()
                .awardId(awardId)
                .awardDesc("未命中规则，走默认规则")
                .build();
    }
}
