package com.luo.domain.service;

import com.luo.domain.entity.vo.ChainResult;
import com.luo.domain.entity.vo.TreeLockResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.chain.IChainFilter;
import com.luo.domain.service.chain.factory.ChainFactory;
import com.luo.domain.service.tree.factory.engine.TreeEngine;
import org.springframework.stereotype.Service;

@Service
public  class AbstractRaffle implements AbstractRaffleService {


    private final ChainFactory chainFactory;

    private final TreeEngine treeEngine;

    public AbstractRaffle(ChainFactory chainFactory, TreeEngine treeEngine) {
        this.chainFactory = chainFactory;
        this.treeEngine = treeEngine;
    }

    @Override
    public Integer abstractRaffle(String username, Integer strategyId) {

        //检测传参
        if (strategyId == null || username.isEmpty()) {
            return null;
        }

        //责任链过滤
        IChainFilter chainFilterComponent = chainFactory.getChainFilterComponent(strategyId);
        ChainResult logic = chainFilterComponent.logic(username, strategyId);

        //规则树过滤
        TreeLockResult treeLockResult = treeEngine.treeEngine(strategyId, logic.getAwardId(), username);

        return treeLockResult.getAwardId();
    }
}
