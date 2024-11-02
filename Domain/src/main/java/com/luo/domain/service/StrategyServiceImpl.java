package com.luo.domain.service;

import com.luo.domain.entity.StrategyEntity;
import com.luo.domain.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StrategyServiceImpl implements IStrategyService{

    @Autowired
    IRepository repository;

    @Override
    public List<StrategyEntity> queryStrategies() {
        return repository.queryStrategyList();
    }
}
