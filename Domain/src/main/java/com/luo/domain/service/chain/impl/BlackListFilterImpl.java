package com.luo.domain.service.chain.impl;

import com.luo.domain.entity.vo.ChainResult;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.domain.service.chain.AbstractChain;
import com.luo.domain.service.chain.IChainFilter;
import com.luo.type.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rule_blacklist")
@Slf4j
public class BlackListFilterImpl extends AbstractChain {


    @Autowired
    private IStrategyRepository repository;


    public String ruleModel(){
        return "rule_blacklist";
    }

    @Override
    public ChainResult processLogic(String username, Integer strategyId) {

        log.info("责任链过滤---黑名单节点");
        String ruleModel = ruleModel();
        String blacklist = repository.queryUserName(ruleModel);
        boolean status = splitString(blacklist,username);
        if (status){
            log.info("责任链过滤---黑名单接管");
            return ChainResult.builder()
                    .awardId(101)
                    .awardDesc("黑名单用户指定返回奖品")
                    .build();
        }
        log.info("责任链过滤---黑名单放行");
        return next().logic(username,strategyId);
    }

    private boolean splitString(String blackList,String username) {
        String[] str = blackList.split(Constants.Feature.COLON);
        String[] str1 = str[1].split(Constants.Feature.COMMA);
        for (String user : str1) {
            if (username.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
