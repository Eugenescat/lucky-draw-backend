package cn.practice.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import cn.practice.infrastructure.dao.po.Strategy;

/**
 * @author yan
 * @description 抽奖策略 DAO
 * @create 2024-11-26
 */
@Mapper
public interface IStrategyDao {

  List<Strategy> queryStrategyList();

}
