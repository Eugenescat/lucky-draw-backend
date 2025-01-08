package cn.practice.domain.award.repository;

import cn.practice.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 奖品仓储服务
 * @create 2024-04-06 09:02
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

}

