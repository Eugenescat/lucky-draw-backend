package cn.practice.domain.activity.service.quota;

import cn.practice.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.practice.domain.activity.model.entity.ActivityCountEntity;
import cn.practice.domain.activity.model.entity.ActivityEntity;
import cn.practice.domain.activity.model.entity.ActivityOrderEntity;
import cn.practice.domain.activity.model.entity.ActivitySkuEntity;
import cn.practice.domain.activity.model.entity.SkuRechargeEntity;
import cn.practice.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.practice.domain.activity.model.valobj.OrderStateVO;
import cn.practice.domain.activity.repository.IActivityRepository;
import cn.practice.domain.activity.service.IRaffleActivitySkuStockService;
import cn.practice.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 抽奖活动服务
 * @create 2024-03-16 08:41
 */
@Service
public class RaffleActivityAccountQuotaService extends AbstractRaffleActivityAccountQuota implements IRaffleActivitySkuStockService {

  public RaffleActivityAccountQuotaService(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
    super(activityRepository, defaultActivityChainFactory);
  }

  @Override
  protected CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
    // 订单实体对象
    ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
    activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
    activityOrderEntity.setSku(skuRechargeEntity.getSku());
    activityOrderEntity.setActivityId(activityEntity.getActivityId());
    activityOrderEntity.setActivityName(activityEntity.getActivityName());
    activityOrderEntity.setStrategyId(activityEntity.getStrategyId());
    // 公司里一般会有专门的雪花算法UUID服务，我们这里直接随机生成个12位就可以了。
    activityOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
    activityOrderEntity.setOrderTime(new Date());
    activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
    activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
    activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
    activityOrderEntity.setState(OrderStateVO.completed);
    activityOrderEntity.setOutBusinessNo(skuRechargeEntity.getOutBusinessNo());

    // 构建聚合对象
    return CreateOrderAggregate.builder()
            .userId(skuRechargeEntity.getUserId())
            .activityId(activitySkuEntity.getActivityId())
            .totalCount(activityCountEntity.getTotalCount())
            .dayCount(activityCountEntity.getDayCount())
            .monthCount(activityCountEntity.getMonthCount())
            .activityOrderEntity(activityOrderEntity)
            .build();
  }

  @Override
  protected void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
    activityRepository.doSaveOrder(createOrderAggregate);
  }

  @Override
  public ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException {
    return activityRepository.takeQueueValue();
  }

  @Override
  public void clearQueueValue() {
    activityRepository.clearQueueValue();
  }

  @Override
  public void updateActivitySkuStock(Long sku) {
    activityRepository.updateActivitySkuStock(sku);
  }

  @Override
  public void clearActivitySkuStock(Long sku) {
    activityRepository.clearActivitySkuStock(sku);
  }

}
