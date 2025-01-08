package cn.practice.domain.award.service;

import cn.practice.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 奖品服务接口
 * @create 2024-04-06 09:03
 */
public interface IAwardService {

    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

}

