package com.luo.infrastructure.dao;

import com.luo.infrastructure.pojo.RuleAward;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRuleAwardDao {

    RuleAward queryRuleAward(RuleAward award);

    String queryUsername(String ruleModel);

    RuleAward queryRuleModel(RuleAward ruleAward);
}
