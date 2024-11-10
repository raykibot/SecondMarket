package com.luo.domain.service.chain;

public interface IChainFilterSet {

    IChainFilter next();

    void setNext(IChainFilter chainFilter);
}
