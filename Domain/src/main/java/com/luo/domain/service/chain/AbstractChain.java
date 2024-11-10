package com.luo.domain.service.chain;

import com.luo.domain.entity.vo.ChainResult;
import com.luo.domain.service.chain.IChainFilter;
import com.luo.domain.service.chain.IChainFilterSet;
import org.springframework.stereotype.Service;


@Service
public abstract class AbstractChain implements IChainFilter {

    private IChainFilter component;

    @Override
    public IChainFilter next() {
        return component;
    }

    @Override
    public void setNext(IChainFilter chainFilter) {
        this.component = chainFilter;
    }

    @Override
    public ChainResult logic(String username, Integer strategyId) {
        return processLogic(username, strategyId);
    }

    public abstract ChainResult processLogic(String username, Integer strategyId);
}
