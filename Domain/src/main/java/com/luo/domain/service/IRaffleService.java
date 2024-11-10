package com.luo.domain.service;

import com.luo.domain.entity.vo.AwardVO;

public interface IRaffleService {


    /**
     * 普通抽奖
     * @param strategyId
     * @return Integer
     */
    Integer raffle(Integer strategyId);


    /**
     * 权重抽奖
     * @param strategyId
     * @param weight
     * @return Integer
     */
    Integer raffleWithWeight(Integer strategyId,Integer weight);




}
