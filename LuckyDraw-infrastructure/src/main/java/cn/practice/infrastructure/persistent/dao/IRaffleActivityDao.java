package cn.practice.infrastructure.persistent.dao;

import cn.practice.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 抽奖活动表Dao
 * @create 2024-03-09 10:04
 */
@Mapper
public interface IRaffleActivityDao {

  RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}