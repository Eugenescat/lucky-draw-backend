package cn.practice.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import cn.practice.infrastructure.persistent.po.Strategy;

/**
 * @author yan
 * @description 抽奖策略 DAO
 * @create 2024-11-26
 */
@Mapper
public interface IStrategyDao {

  List<Strategy> queryStrategyList();

  Strategy queryStrategyByStrategyId(Long strategyId);

}
