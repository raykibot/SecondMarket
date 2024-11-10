package com.luo.domain.service.tree.factory.engine;

import com.luo.domain.entity.vo.TreeLockResult;

public interface ITreeEngine {


    TreeLockResult treeEngine(Integer strategyId,Integer awardId,String username);


}
