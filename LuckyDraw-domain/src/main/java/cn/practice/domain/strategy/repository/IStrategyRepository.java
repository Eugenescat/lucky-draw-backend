package cn.practice.domain.strategy.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.practice.domain.strategy.model.entity.StrategyAwardEntity;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 策略服务仓储接口
 * @create 2023-12-23 09:33
 */
public interface IStrategyRepository {

  List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

  void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

  Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey);

  int getRateRange(Long strategyId);

}
