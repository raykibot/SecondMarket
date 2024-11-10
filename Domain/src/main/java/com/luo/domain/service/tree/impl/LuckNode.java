package com.luo.domain.service.tree.impl;

import com.luo.domain.entity.vo.TreeLockResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.tree.ILockTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("node_luck")
@Slf4j
public class LuckNode implements ILockTree {


    @Autowired
    private IStrategyRepository repository;

    public String treeNodeName(){
        return "node_luck";
    }

    @Override
    public TreeLockResult treeFilter(Integer awardId, String username,Integer strategyId,String treeId) {

        log.info("规则树过滤---兜底节点");

        return TreeLockResult.builder()
                .awardId(101)
                .build();
    }
}
