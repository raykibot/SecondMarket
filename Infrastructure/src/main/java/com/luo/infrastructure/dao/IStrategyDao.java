package com.luo.infrastructure.dao;

import com.luo.infrastructure.pojo.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyDao {


    List<Strategy> queryStrategyList();



}
