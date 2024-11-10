package com.luo.domain.service.assembled;


import com.luo.domain.entity.RuleAwardEntity;
import com.luo.domain.entity.StrategyAwardEntity;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.type.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StrategyAssembled implements IStrategyAssembled{


    @Autowired
    private IStrategyRepository repository;


    @Override
    public void assembled(Integer strategyId) {

        if (strategyId == null){
            return;
        }

        //1.查询策略配置 获取所有奖品信息
        List<StrategyAwardEntity> list = repository.queryAwardListByStrategyId(strategyId);

        //2.最小概率除以1获取全部范围
        Optional<StrategyAwardEntity> minRate = list.stream().min(Comparator
                .comparing(StrategyAwardEntity::getRate));

        int range = minRate.map(entity -> (int) Math.ceil(1 / entity.getRate().doubleValue()))
                .orElse(0);  // 若无最小概率，返回 0（此时可做异常处理）

        //3.生成奖品查找表

        List<Integer> awardIds = new ArrayList<>(range);//临时存储所有奖品id（根据概率大小决定个数）

        for (StrategyAwardEntity strategyAwardEntity : list) {
            BigDecimal rate = strategyAwardEntity.getRate();
            Integer count = (int) Math.ceil(rate.doubleValue() * range);

            for (Integer i = 0; i < count; i++) {
                awardIds.add(strategyAwardEntity.getAwardId());
            }
        }
        //log.info("awardIds:{}",awardIds);

        //打乱奖品id集合顺序
        Collections.shuffle(awardIds);

        Map<Integer,Integer> awardMap = new HashMap<>();

        for (int i = 0; i < range; i++) {
            awardMap.put(i,awardIds.get(i));
        }


        //存储奖品库存信息
        String cacheKeySurplus = Constants.TreeNodeType.AWARD_COUNT_KEY;
        repository.setAwardSurplus(cacheKeySurplus);

        //log.info("awardMap:{}",awardMap);
        String cacheKey1 = Constants.RedisKey.AWARD_TABLE_KEY.concat(Constants.Feature.UNDERLINE)+strategyId;//查找表key
        String cacheKey2 = Constants.RedisKey.AWARD_TABLE_RANGE_KEY.concat(Constants.Feature.UNDERLINE)+strategyId;//查找表范围key

        //存储权重奖品查找表
        String cacheKey3 = Constants.RedisKey.AWARD_WEIGHT_TABLE_KEY.concat(Constants.Feature.UNDERLINE)+strategyId;//权重查找表key
        Map<Integer,List<Integer>> weightKeyList = improveSplitRuleValue(strategyId);
        Set<Integer> keys = weightKeyList.keySet();
        for (Integer key : keys) {
            //存储权重奖品查找表key
            repository.setWeightAwardTable(cacheKey3.concat(Constants.Feature.UNDERLINE)+key,weightKeyList.get(key));
        }


        //存储到redis中
        repository.setAwardTable(cacheKey1,cacheKey2,awardMap,range);
    }

    private Map<Integer,List<Integer>> splitRuleValue (Integer strategyId){
        String ruleModel = "rule_weight";
        RuleAwardEntity ruleAwardEntity = repository.getRuleAwardList(strategyId,ruleModel);
        String ruleValue = ruleAwardEntity.getRuleValue();
        //log.info("ruleValue:{}",ruleValue);
        //4000:101,102,103,104,105 5000:101,102,103,104,105,106,107 6000:107,108,109
        String[] str1 = ruleValue.split(Constants.Feature.SPACE);
        Map<Integer,List<Integer> > weight = new HashMap<>();
        for (String str : str1) {
            String[] str2 = str.split(Constants.Feature.COLON);
            String[] str3 = str2[1].split(Constants.Feature.COMMA);
            List<Integer> list = new ArrayList<>();
            for (String awardId : str3) {
                list.add(Integer.parseInt(awardId));
            }
            weight.put(Integer.parseInt(str2[0]),list);
        }
        return weight;
    }

    private Map<Integer,List<Integer>> improveSplitRuleValue(Integer strategyId){
        String ruleModel = "rule_weight";
        RuleAwardEntity ruleAwardEntity = repository.getRuleAwardList(strategyId,ruleModel);
        String ruleValue = ruleAwardEntity.getRuleValue();
        String[] str1 = ruleValue.split(Constants.Feature.SPACE);
        Map<Integer, List<Integer>> weight = Arrays.stream(str1)
                .map(str -> str.split(Constants.Feature.COLON))
                .collect(Collectors.toMap(
                        str2 -> Integer.parseInt(str2[0]),
                        str2 -> Arrays.stream(str2[1].split(Constants.Feature.COMMA))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList())
                ));
        return weight;
    }
}
