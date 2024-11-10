package com.luo.domain.service.tree.factory.engine;


import com.luo.domain.entity.RuleAwardEntity;
import com.luo.domain.entity.vo.TreeLockResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.tree.ILockTree;
import com.luo.domain.service.tree.factory.TreeFactory;
import com.luo.type.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TreeEngine implements ITreeEngine {


    private final TreeFactory treeFactory;

    private final IStrategyRepository repository;

    public TreeEngine(TreeFactory treeFactory, IStrategyRepository repository) {
        this.treeFactory = treeFactory;
        this.repository = repository;
    }

    @Override
    public TreeLockResult treeEngine(Integer strategyId, Integer awardId, String username) {

        TreeLockResult result = new TreeLockResult();

        Map<String, ILockTree> treeMap = treeFactory.getNode();

        Set<String> keys = treeMap.keySet();
        for (int i = 0; i < keys.size(); i++) {
            ILockTree iLockTree = treeMap.get("node_lock");
            result = iLockTree.treeFilter(awardId, username, strategyId, null);
            if (result.getNodeLimitResult().equals(Constants.TreeNodeType.ALLOW )) {
                ILockTree nextNode = treeMap.get(result.getNodeNameTo());
                result =  nextNode.treeFilter(awardId, username, strategyId, result.getTreeId());
                if (result.getNodeNameTo() == null){
                    return result;
                }
            }

            if (result.getNodeNameTo() == null){
                return result;
            }
            ILockTree lockTree = treeMap.get(result.getNodeNameTo());
            result = lockTree.treeFilter(awardId, username, strategyId, null);
            if (result.getNodeLimitResult() == null) {
                return result;
            }

        }
        return null;
    }
}
