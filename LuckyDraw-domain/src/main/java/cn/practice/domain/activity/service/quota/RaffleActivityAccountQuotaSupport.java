package cn.practice.domain.activity.service.quota;

import cn.practice.domain.activity.model.entity.ActivityCountEntity;
import cn.practice.domain.activity.model.entity.ActivityEntity;
import cn.practice.domain.activity.model.entity.ActivitySkuEntity;
import cn.practice.domain.activity.repository.IActivityRepository;
import cn.practice.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

public class RaffleActivityAccountQuotaSupport {

  protected IActivityRepository activityRepository;
  protected DefaultActivityChainFactory defaultActivityChainFactory;;

  public RaffleActivityAccountQuotaSupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
    this.activityRepository = activityRepository;
    this.defaultActivityChainFactory = defaultActivityChainFactory;
  }

  public ActivitySkuEntity queryActivitySku(Long sku) {
    return activityRepository.queryActivitySku(sku);
  }

  public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
    return activityRepository.queryRaffleActivityByActivityId(activityId);
  }

  public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
    return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
  }

}
