package com.luo.domain.service.tree.impl;

import com.luo.domain.entity.NodeEntity;
import com.luo.domain.entity.TreeNodeLineEntity;
import com.luo.domain.entity.vo.TreeLockResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.tree.ILockTree;
import com.luo.type.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("node_stock")
@Slf4j
public class StockNode implements ILockTree {

    @Autowired
    private IStrategyRepository repository;


    public String noeName() {
        return "node_stock";
    }

    @Override
    public TreeLockResult treeFilter(Integer awardId, String username, Integer strategyId,String treeId) {

        NodeEntity nodeEntity = repository.queryNode(treeId, "node_stock");
        List<TreeNodeLineEntity> list = nodeEntity.getList();

        log.info("规则树过滤---库存扣减节点");
        Boolean result = repository.reduceSurplus(strategyId, awardId);
        if (result) {
            log.info("规则树过滤---库存扣减节点接管");
            //库存扣减成功
            return TreeLockResult.builder()
                    .awardId(awardId)
                    .build();
        }
        log.info("规则树过滤---库存扣减节点放行");
        TreeNodeLineEntity nodeLine = getNextNode(list);
        return TreeLockResult.builder()
                .awardId(awardId)
                .nodeNameTo(nodeLine.getNodeNameTo())
                .nodeLimitResult(Constants.TreeNodeType.ALLOW)
                .build();
    }

    private TreeNodeLineEntity getNextNode(List<TreeNodeLineEntity> list) {
        return list.stream()
                .filter(entity -> entity.getNodeLimitResult().equals(Constants.TreeNodeType.ALLOW))
                .findFirst()
                .orElseGet(() ->
                        list.stream()
                                .filter(entity -> entity.getNodeLimitResult().equals(Constants.TreeNodeType.TAKE_OVER))
                                .findFirst()
                                .orElse(null)
                );
    }
}
