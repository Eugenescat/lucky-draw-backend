package cn.practice.domain.activity.repository;

import cn.practice.domain.activity.aggregate.CreateOrderAggregate;
import cn.practice.domain.activity.model.entity.ActivityCountEntity;
import cn.practice.domain.activity.model.entity.ActivityEntity;
import cn.practice.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 活动仓储接口
 * @create 2024-03-16 10:31
 */
public interface IActivityRepository {

  ActivitySkuEntity queryActivitySku(Long sku);

  ActivityEntity queryRaffleActivityByActivityId(Long activityId);

  ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

  void doSaveOrder(CreateOrderAggregate createOrderAggregate);
}
