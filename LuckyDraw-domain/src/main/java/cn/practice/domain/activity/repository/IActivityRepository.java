package cn.practice.domain.activity.repository;

import cn.practice.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.practice.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import cn.practice.domain.activity.model.entity.*;
import cn.practice.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;

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

  void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

  boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

  void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

  ActivitySkuStockKeyVO takeQueueValue();

  void clearQueueValue();

  void updateActivitySkuStock(Long sku);

  void clearActivitySkuStock(Long sku);

  UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

  ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

  ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

  ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

  void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);


}
