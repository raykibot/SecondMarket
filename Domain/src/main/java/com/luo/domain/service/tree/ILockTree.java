package com.luo.domain.service.tree;

import com.luo.domain.entity.vo.TreeLockResult;

public interface ILockTree {


    /**
     * 规则树过滤
     * @param awardId
     * @param username
     * @return TreeLockResult
     */
    TreeLockResult treeFilter(Integer awardId, String username,Integer strategyId,String treeId);






}
