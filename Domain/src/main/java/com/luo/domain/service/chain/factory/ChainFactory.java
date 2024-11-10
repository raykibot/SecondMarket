package com.luo.domain.service.chain.factory;

import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.chain.IChainFilter;
import com.luo.type.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChainFactory {

    private final Map<String, IChainFilter> filterGroups ;

    @Autowired
    private IStrategyRepository repository;

    public ChainFactory(Map<String, IChainFilter> filterGroups, IStrategyRepository repository) {
        this.filterGroups = filterGroups;
        this.repository = repository;
    }

    public IChainFilter getChainFilterComponent(Integer strategyId){

        //查询对应策略有多少种规则模型
        String ruleModels = repository.queryRuleModels(strategyId);
        String[] str = ruleModels.split(Constants.Feature.COMMA);
        IChainFilter head = filterGroups.get(str[0]);
        //初始化头节点 让指针指向头节点
        IChainFilter current = head;

        for (int i = 1; i < str.length; i++) {
            IChainFilter nextFilter = filterGroups.get(str[i]);
            if (nextFilter != null){
                current.setNext(nextFilter);//将当前节点的下一个节点设为新节点
                current = nextFilter;//更新当前节点
            }
        }
        return head;
    }

}



