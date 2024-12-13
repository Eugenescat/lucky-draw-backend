package cn.practice.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import cn.practice.infrastructure.persistent.po.StrategyAward;

/**
 * @author yan
 * @description 抽奖策略奖品明细配置 - 概率、规则 DAO
 * @create 2024-11-26
 */
@Mapper
public interface IStrategyAwardDao {

  List<StrategyAward> queryStrategyAwardList();

  List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

  String queryStrategyAwardRuleModels(StrategyAward strategyAward);

  void updateStrategyAwardStock(StrategyAward strategyAward);

  StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);
}

