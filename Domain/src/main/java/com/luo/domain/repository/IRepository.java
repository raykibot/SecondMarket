package com.luo.domain.repository;

import com.luo.domain.entity.StrategyEntity;

import java.util.List;

public interface IRepository {

    List<StrategyEntity> queryStrategyList();

}
