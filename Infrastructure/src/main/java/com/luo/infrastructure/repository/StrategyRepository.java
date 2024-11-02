package com.luo.infrastructure.repository;

import com.luo.domain.entity.StrategyEntity;
import com.luo.domain.repository.IRepository;
import com.luo.infrastructure.dao.IStrategyDao;
import com.luo.infrastructure.pojo.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StrategyRepository implements IRepository {


    @Autowired
    private IStrategyDao strategyDao;

    @Override
    public List<StrategyEntity> queryStrategyList() {
        List<Strategy> strategyList = strategyDao.queryStrategyList();

        List<StrategyEntity> strategyEntities = new ArrayList<>();
        for (Strategy strategy : strategyList) {

            StrategyEntity build = StrategyEntity.builder()
                    .strategyId(strategy.getStrategyId())
                    .strategyDesc(strategy.getStrategyDesc())
                    .ruleModels(strategy.getRuleModels())
                    .createTime(strategy.getCreateTime())
                    .updateTime(strategy.getUpdateTime())
                    .build();

            strategyEntities.add(build);
        }
        return strategyEntities;
    }
}
