package com.luo.infrastructure.dao;

import com.luo.infrastructure.pojo.Strategy;
import com.luo.infrastructure.pojo.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyDao {


    List<Strategy> queryStrategyList();

    List<StrategyAward> queryStrategyAward(Integer strategyId);

    String queryRuleModels(Integer strategyId);

    Integer queryAwardSurplus(StrategyAward strategyAward);

    void updateSurplus(Integer awardId);

    List<StrategyAward> querySurplus();
}
