package com.luo.domain.service.chain;

import com.luo.domain.entity.vo.ChainResult;

public interface IChainFilter extends IChainFilterSet {


    ChainResult logic(String username, Integer strategyId);

}
