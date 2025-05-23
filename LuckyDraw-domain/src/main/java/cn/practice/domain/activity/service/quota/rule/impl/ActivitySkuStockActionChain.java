package cn.practice.domain.activity.service.quota.rule.impl;

import cn.practice.domain.activity.model.entity.ActivityCountEntity;
import cn.practice.domain.activity.model.entity.ActivityEntity;
import cn.practice.domain.activity.model.entity.ActivitySkuEntity;
import cn.practice.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.practice.domain.activity.repository.IActivityRepository;
import cn.practice.domain.activity.service.armory.IActivityDispatch;
import cn.practice.domain.activity.service.quota.rule.AbstractActionChain;
import cn.practice.types.exception.AppException;
import cn.practice.types.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 商品库存规则节点
 * @create 2024-03-23 10:25
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {
  @Resource
  private IActivityDispatch activityDispatch;
  @Resource
  private IActivityRepository activityRepository;


  @Override
  public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
    log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
    // 扣减库存
    boolean status = activityDispatch.subtractionActivitySkuStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());
    // true；库存扣减成功
    if (status) {
      log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】成功。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());

      // 写入延迟队列，延迟消费更新库存记录
      activityRepository.activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO.builder()
              .sku(activitySkuEntity.getSku())
              .activityId(activityEntity.getActivityId())
              .build());

      return true;
    }

    throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
  }

}

