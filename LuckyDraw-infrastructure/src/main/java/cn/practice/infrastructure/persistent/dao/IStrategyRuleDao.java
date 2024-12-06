package cn.practice.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import cn.practice.infrastructure.persistent.po.StrategyRule;

/**
 * @author yan
 * @description 策略规则 DAO
 * @create 2024-11-26
 */
@Mapper
public interface IStrategyRuleDao {

  List<StrategyRule> queryStrategyRuleList();

  StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);

  String queryStrategyRuleValue(StrategyRule strategyRule);
}

