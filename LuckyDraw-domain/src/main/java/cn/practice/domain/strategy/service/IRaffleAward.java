package cn.practice.domain.strategy.service;

import java.util.List;

import cn.practice.domain.strategy.model.entity.StrategyAwardEntity;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 策略奖品接口
 * @create 2024-02-14 16:44
 */
public interface IRaffleAward {

  /**
   * 根据策略ID查询抽奖奖品列表配置
   *
   * @param strategyId 策略ID
   * @return 奖品列表
   */
  List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

}

