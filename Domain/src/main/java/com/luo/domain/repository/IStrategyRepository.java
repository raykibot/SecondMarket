package com.luo.domain.repository;

import com.luo.domain.entity.NodeEntity;
import com.luo.domain.entity.RuleAwardEntity;
import com.luo.domain.entity.StrategyAwardEntity;
import com.luo.domain.entity.StrategyEntity;
import com.luo.domain.entity.vo.AwardVO;

import java.util.List;
import java.util.Map;

public interface IStrategyRepository {


    List<StrategyAwardEntity> queryAwardListByStrategyId(Integer strategyId);

    void setAwardTable(String cacheKey1, String cacheKey2, Map<Integer, Integer> awardMap, int range);

    Integer getAwardTableRange(Integer strategyId);

    Integer getAwardWithIndex(Integer range, Integer strategyId);

    RuleAwardEntity getRuleAwardList(Integer strategyId, String ruleModel);

    void setWeightAwardTable(String key, List<Integer> awardIdList);

    Integer getWeightAward(Integer strategyId, Integer weight);

    String queryRuleModels(Integer strategyId);

    String queryUserName(String ruleModel);

    RuleAwardEntity queryAwardRuleModel(Integer awardId, Integer strategyId);

    String queryTreeId(String ruleModel);

    NodeEntity queryNode(String treeKey, String nodeName);

    Boolean reduceSurplus(Integer strategyId, Integer awardId);

    void setAwardSurplus(String cacheKeySurplus);
}
