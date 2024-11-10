package com.luo.infrastructure.repository;

import com.luo.domain.entity.*;
import com.luo.domain.repository.IStrategyRepository;
import com.luo.infrastructure.dao.IRuleAwardDao;
import com.luo.infrastructure.dao.IStrategyDao;
import com.luo.infrastructure.dao.ITreeNodeDao;
import com.luo.infrastructure.pojo.*;
import com.luo.infrastructure.redis.IRedisClient;
import com.luo.type.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class StrategyRepository implements IStrategyRepository {


    @Autowired
    private IStrategyDao strategyDao;
    @Autowired
    private IRuleAwardDao ruleAwardDao;
    @Autowired
    private ITreeNodeDao treeNodeDao;
    @Autowired
    private IRedisClient redisClient;

    @Override
    public List<StrategyAwardEntity> queryAwardListByStrategyId(Integer strategyId) {
        //装配策略时需要查询策略奖品列表


        //优先从redis中查找
        RBucket<List<StrategyAwardEntity>> value = redisClient.getValue(Constants.RedisKey.STRATEGY_AWARD_LIST_KEY.concat(Constants.Feature.UNDERLINE) + strategyId);
        List<StrategyAwardEntity> list = value.get();
        if (list != null) {
            return list;
        }


        List<StrategyAward> strategyAwardList = strategyDao.queryStrategyAward(strategyId);

        //如果redis中为空，重新初始化list（避免空指针报错）
        list = new ArrayList<>();

        for (StrategyAward strategyAward : strategyAwardList) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .sort(strategyAward.getSort())
                    .awardCount(strategyAward.getAwardCount())
                    .awardSurplus(strategyAward.getAwardSurplus())
                    .rate(strategyAward.getRate())
                    .awardTitle(strategyAward.getAwardTitle())
                    .awardSubtitle(strategyAward.getAwardSubtitle())
                    .ruleModel(strategyAward.getRuleModel())
                    .createTime(strategyAward.getCreateTime())
                    .updateTime(strategyAward.getUpdateTime())
                    .build();
            list.add(strategyAwardEntity);
        }

        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_LIST_KEY.concat(Constants.Feature.UNDERLINE) + strategyId;
        redisClient.setValue(cacheKey, list);

        return list;
    }

    @Override
    public void setAwardTable(String cacheKey1, String cacheKey2, Map<Integer, Integer> awardMap, int range) {

        //存储到redis中
        redisClient.setValue(cacheKey1, awardMap);//存储奖品查找表

        redisClient.setValue(cacheKey2, range);//存储奖品范围查找表
    }

    @Override
    public Integer getAwardTableRange(Integer strategyId) {

        String cacheKey = Constants.RedisKey.AWARD_TABLE_RANGE_KEY.concat(Constants.Feature.UNDERLINE) + strategyId;
        RBucket<Integer> bucket = redisClient.getValue(cacheKey);
        return bucket.get();
    }

    @Override
    public Integer getAwardWithIndex(Integer range, Integer strategyId) {

        String cacheKey = Constants.RedisKey.AWARD_TABLE_KEY.concat(Constants.Feature.UNDERLINE) + strategyId;

        RBucket<Map<Integer, Integer>> bucket = redisClient.getValue(cacheKey);

        //随机生成范围内索引  找到对应的奖品ID
        Map<Integer, Integer> map = bucket.get();

        Random random = new Random();
        Integer index = random.nextInt(range);
        return map.get(index.toString());


    }

    @Override
    public RuleAwardEntity getRuleAwardList(Integer strategyId, String ruleModel) {

        RuleAward award = new RuleAward();
        award.setStrategyId(strategyId);
        award.setRuleModel(ruleModel);
        RuleAward ruleAward = ruleAwardDao.queryRuleAward(award);
        return RuleAwardEntity.builder()
                .ruleValue(ruleAward.getRuleValue())
                .build();
    }

    @Override
    public void setWeightAwardTable(String key, List<Integer> awardIdList) {
        redisClient.setValue(key, awardIdList);
    }

    @Override
    public Integer getWeightAward(Integer strategyId, Integer weight) {

        //优先从redis中查找权重奖品查找表
        String cacheKey = Constants.RedisKey.AWARD_WEIGHT_TABLE_KEY.concat(Constants.Feature.UNDERLINE) + strategyId;
        RBucket<List<Integer>> bucket = redisClient.getValue(cacheKey.concat(Constants.Feature.UNDERLINE) + weight);
        List<Integer> awardIdList = bucket.get();

        return awardIdList.get(new Random().nextInt(awardIdList.size()));
    }

    @Override
    public String queryRuleModels(Integer strategyId) {
        return strategyDao.queryRuleModels(strategyId);
    }

    @Override
    public String queryUserName(String ruleModel) {
        return ruleAwardDao.queryUsername(ruleModel);
    }

    @Override
    public RuleAwardEntity queryAwardRuleModel(Integer awardId, Integer strategyId) {

        RuleAward ruleAward = new RuleAward();
        ruleAward.setAwardId(awardId);
        ruleAward.setStrategyId(strategyId);

        RuleAward result = ruleAwardDao.queryRuleModel(ruleAward);

        return RuleAwardEntity.builder()
                .ruleModel(result.getRuleModel())
                .ruleValue(result.getRuleValue())
                .build();
    }

    @Override
    public String queryTreeId(String ruleModel) {
        return treeNodeDao.queryTreeId(ruleModel);
    }

    @Override
    public NodeEntity queryNode(String treeKey, String nodeName) {

        Node node = new Node();
        node.setTreeId(treeKey);
        node.setNodeName(nodeName);

        //查询节点信息
        Node result = treeNodeDao.queryNodeEntity(node);


        //根据节点名称和规则树ID查询节点连线
        TreeNodeLine treeNodeLine = new TreeNodeLine();
        treeNodeLine.setTreeId(treeKey);
        treeNodeLine.setNodeNameFrom(nodeName);
        List<TreeNodeLine> lineList = treeNodeDao.queryNodeLine(treeNodeLine);

        List<TreeNodeLineEntity> entityLineList = new ArrayList<>();
        for (TreeNodeLine nodeLine : lineList) {
            TreeNodeLineEntity entity = TreeNodeLineEntity.builder()
                    .nodeNameFrom(nodeLine.getNodeNameFrom())
                    .nodeNameTo(nodeLine.getNodeNameTo())
                    .nodeLimitResult(nodeLine.getNodeLimitResult())
                    .nodeLineDesc(nodeLine.getNodeLineDesc())
                    .build();
            entityLineList.add(entity);
        }
        return NodeEntity.builder()
                .nodeName(result.getNodeName())
                .nodeValue(result.getNodeValue())
                .nodeDesc(result.getNodeDesc())
                .list(entityLineList)
                .build();
    }

    @Override
    public Boolean reduceSurplus(Integer strategyId, Integer awardId) {

        // 定义 Redis 缓存键
        String cacheKey = Constants.TreeNodeType.AWARD_COUNT_KEY;

        // 获取库存信息
        RBucket<List<Map<String, Integer>>> bucket = redisClient.getValue(cacheKey);
        List<Map<String, Integer>> list = bucket.get();

        // 遍历查找指定 awardId 的库存
        // 遍历查找指定 awardId 的库存
        for (Map<String, Integer> awardSurplus : list) {
            if (awardSurplus.containsKey(String.valueOf(awardId))) {
                Integer surplus = awardSurplus.get(String.valueOf(awardId));
                // 如果库存存在且大于0
                if (surplus != null && surplus > 0) {
                    int updatedSurplus = surplus - 1;

                    // 更新 Redis 中的库存数据
                    awardSurplus.put(String.valueOf(awardId), updatedSurplus);
                    redisClient.setValue(cacheKey, list);

                    // 更新数据库中的库存
                    strategyDao.updateSurplus(awardId);


                    return true;
                }
            }
        }


        // 库存不足时返回 false
        return false;
    }

    @Override
    public void setAwardSurplus(String cacheKeySurplus) {

        List<StrategyAward> list = strategyDao.querySurplus();
        List<Map<Integer, Integer>> awardSurplus = new ArrayList<>(list.size());
        for (StrategyAward strategyAward : list) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(strategyAward.getAwardId(), strategyAward.getAwardSurplus());
            awardSurplus.add(map);
        }
        //存储到redis中
        redisClient.setValue(cacheKeySurplus, awardSurplus);
    }
}
