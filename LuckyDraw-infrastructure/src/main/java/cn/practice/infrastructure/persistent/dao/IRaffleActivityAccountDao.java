package cn.practice.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;

import cn.practice.infrastructure.persistent.po.RaffleActivityAccount;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 抽奖活动账户表
 * @create 2024-03-09 10:05
 */
@Mapper
public interface IRaffleActivityAccountDao {

  void insert(RaffleActivityAccount raffleActivityAccount);

  int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

  @DBRouter
  RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount raffleActivityAccountReq);

  int updateActivityAccountSubtractionQuota(RaffleActivityAccount raffleActivityAccount);

  int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccount raffleActivityAccount);

  int updateActivityAccountDaySubtractionQuota(RaffleActivityAccount raffleActivityAccount);

  void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount raffleActivityAccount);

  void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount raffleActivityAccount);


}

