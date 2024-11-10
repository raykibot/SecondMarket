package com.luo.domain.service.tree.impl;

import com.luo.domain.entity.NodeEntity;
import com.luo.domain.entity.RuleAwardEntity;
import com.luo.domain.entity.TreeNodeLineEntity;
import com.luo.domain.entity.vo.TreeLockResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.tree.ILockTree;
import com.luo.type.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("node_lock")
@Slf4j
public class LockNode implements ILockTree {


    @Autowired
    private IStrategyRepository repository;
    private Integer raffleCount = 0;


    public String ruleModel() {
        return "rule_lock";
    }

    /**
     * 次数锁节点
     *
     * @return "node_lock"
     */
    public String treeNodeName() {
        return "node_lock";
    }

    @Override
    public TreeLockResult treeFilter(Integer awardId, String username, Integer strategyId,String treeId) {

        log.info("规则树过滤---次数锁节点");
        //查询奖品是否为上锁奖品
        RuleAwardEntity ruleAwardEntity = repository.queryAwardRuleModel(awardId, strategyId);

        //查询规则树ID
        String treeKey = repository.queryTreeId(ruleModel());

        //查询节点（节点中包含节点对应的连线集合）
        NodeEntity entity = repository.queryNode(treeKey, treeNodeName());
        List<TreeNodeLineEntity> list = entity.getList();

        if (ruleAwardEntity.getRuleModel().equals(ruleModel())) {

            if (raffleCount >= Integer.parseInt(ruleAwardEntity.getRuleValue())) {
                //满足奖品解锁条件 去到库存扣减节点
                log.info("规则树过滤---次数锁节点放行");
                TreeNodeLineEntity treeNodeLineEntity = getNextNode(list);

                //返回节点处理完成的信息
                return TreeLockResult.builder()
                        .awardId(awardId)
                        .nodeNameTo(treeNodeLineEntity.getNodeNameTo())
                        .nodeLimitResult(treeNodeLineEntity.getNodeLimitResult())
                        .treeId(treeKey)
                        .build();
            }

            //未满足奖品解锁条件 去到默认节点
            log.info("规则树过滤---次数锁节点接管");
            TreeNodeLineEntity nodeLine = getNextNode1(list);
            return TreeLockResult.builder()
                    .awardId(awardId)
                    .nodeNameTo(nodeLine.getNodeNameTo())
                    .nodeLimitResult(nodeLine.getNodeLimitResult())
                    .treeId(treeKey)
                    .build();
        }
        //不是上锁奖品,直接去到默认节点
        return TreeLockResult.builder()
                .awardId(awardId)
                .nodeLimitResult(Constants.TreeNodeType.TAKE_OVER)
                .nodeNameTo(null)
                .build();
    }

    //获取下一个节点
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

    private TreeNodeLineEntity getNextNode1(List<TreeNodeLineEntity> list) {
        return list.stream()
                .filter(entity -> entity.getNodeLimitResult().equals(Constants.TreeNodeType.TAKE_OVER))
                .findFirst()
                .orElseGet(() ->
                        list.stream()
                                .filter(entity -> entity.getNodeLimitResult().equals(Constants.TreeNodeType.ALLOW))
                                .findFirst()
                                .orElse(null)
                );
    }

}
