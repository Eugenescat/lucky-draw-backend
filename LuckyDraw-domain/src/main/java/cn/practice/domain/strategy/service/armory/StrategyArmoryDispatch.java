package cn.practice.domain.strategy.service.armory;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

import cn.practice.types.common.Constants;
import cn.practice.domain.strategy.model.entity.StrategyAwardEntity;
import cn.practice.domain.strategy.model.entity.StrategyEntity;
import cn.practice.domain.strategy.model.entity.StrategyRuleEntity;
import cn.practice.domain.strategy.repository.IStrategyRepository;
import cn.practice.types.enums.ResponseCode;
import cn.practice.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 策略装配库(兵工厂)，负责初始化策略计算
 * @create 2023-12-23 10:02
 */

@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

  @Resource
  private IStrategyRepository repository;

  private final SecureRandom secureRandom = new SecureRandom();

  @Override
  public boolean assembleLotteryStrategy(Long strategyId) {
    // 1. 查询策略配置
    List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);

    // 2 缓存奖品库存【用于decr扣减库存使用】
    for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
      Integer awardId = strategyAward.getAwardId();
      Integer awardCount = strategyAward.getAwardCount();
      cacheStrategyAwardCount(strategyId, awardId, awardCount);
    }

    // 3.1 默认装配配置【全量抽奖概率】
    assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

    // 3.2 权重策略配置 - 适用于 rule_weight 权重规则配置
    StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
    String ruleWeight = strategyEntity.getRuleWeight();
    if (null == ruleWeight) return true;

    StrategyRuleEntity strategyRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);
    if (null == strategyRuleEntity) {
      throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
    }
    Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
    Set<String> keys = ruleWeightValueMap.keySet();
    for (String key : keys) {
      List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
      ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
      strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
      assembleLotteryStrategy(String.valueOf(strategyId).concat(Constants.UNDERLINE).concat(key), strategyAwardEntitiesClone);
    }

    return true;
  }

  /**
   * 缓存奖品库存到Redis
   *
   * @param strategyId 策略ID
   * @param awardId    奖品ID
   * @param awardCount 奖品库存
   */
  private void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount) {
    String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
    repository.cacheStrategyAwardCount(cacheKey, awardCount);
  }

  private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
    // 1. 获取最小概率值
    BigDecimal minAwardRate = strategyAwardEntities.stream()
            .map(StrategyAwardEntity::getAwardRate)
            .min(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

    // 2. 获取概率值总和
    BigDecimal totalAwardRate = strategyAwardEntities.stream()
            .map(StrategyAwardEntity::getAwardRate)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // 3. 用 1 % 0.0001 获得概率范围，百分位、千分位、万分位
    BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

    // 4. 生成策略奖品概率查找表「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
    List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
    for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
      Integer awardId = strategyAward.getAwardId();
      BigDecimal awardRate = strategyAward.getAwardRate();
      // 计算出每个概率值需要存放到查找表的数量，循环填充
      for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
        strategyAwardSearchRateTables.add(awardId);
      }
    }

    // 5. 对存储的奖品进行乱序操作
    Collections.shuffle(strategyAwardSearchRateTables);

    // 6. 生成出Map集合，key值，对应的就是后续的概率值。通过概率来获得对应的奖品ID
    Map<Integer, Integer> shuffleStrategyAwardSearchRateTable = new LinkedHashMap<>();
    for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
      shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
    }

    // 7. 存放到 Redis
    repository.storeStrategyAwardSearchRateTable(key, shuffleStrategyAwardSearchRateTable.size(), shuffleStrategyAwardSearchRateTable);
  }

  @Override
  public Integer getRandomAwardId(Long strategyId) {
    // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
    int rateRange = repository.getRateRange(strategyId);
    // 通过生成的随机值，获取概率值奖品查找表的结果
    return repository.getStrategyAwardAssemble(String.valueOf(strategyId), secureRandom.nextInt(rateRange));
  }

  @Override
  public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
    String key = String.valueOf(strategyId).concat(Constants.UNDERLINE).concat(ruleWeightValue);
    return getRandomAwardId(key);
  }

  @Override
  public Integer getRandomAwardId(String key) {
    // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
    int rateRange = repository.getRateRange(key);
    // 通过生成的随机值，获取概率值奖品查找表的结果
    return repository.getStrategyAwardAssemble(key, secureRandom.nextInt(rateRange));
  }

  @Override
  public Boolean subtractionAwardStock(Long strategyId, Integer awardId) {
    String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
    return repository.subtractionAwardStock(cacheKey);
  }

}
