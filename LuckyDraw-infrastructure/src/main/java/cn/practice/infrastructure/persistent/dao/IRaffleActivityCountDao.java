package cn.practice.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.practice.infrastructure.persistent.po.RaffleActivityCount;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 抽奖活动次数配置表Dao
 * @create 2024-03-09 10:07
 */
@Mapper
public interface IRaffleActivityCountDao {
  RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);
}
