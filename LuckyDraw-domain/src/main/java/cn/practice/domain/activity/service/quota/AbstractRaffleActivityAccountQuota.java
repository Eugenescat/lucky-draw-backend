package cn.practice.domain.activity.service.quota;

import cn.practice.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.practice.domain.activity.model.entity.ActivityCountEntity;
import cn.practice.domain.activity.model.entity.ActivityEntity;
import cn.practice.domain.activity.model.entity.ActivitySkuEntity;
import cn.practice.domain.activity.model.entity.SkuRechargeEntity;
import cn.practice.domain.activity.repository.IActivityRepository;
import cn.practice.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.practice.domain.activity.service.quota.rule.IActionChain;
import cn.practice.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import lombok.extern.slf4j.Slf4j;
import cn.practice.types.enums.ResponseCode;
import cn.practice.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 抽奖活动抽象类，定义标准的流程
 * @create 2024-03-16 08:42
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {

  public AbstractRaffleActivityAccountQuota(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
    super(activityRepository, defaultActivityChainFactory);
  }


  @Override
  public String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity) {
    // 1. 参数校验
    String userId = skuRechargeEntity.getUserId();
    Long sku = skuRechargeEntity.getSku();
    String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
    if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
      throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
    }
    // 2. 查询基础信息
    // 2.1 通过sku查询活动信息
    ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
    // 2.2 查询活动信息
    ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
    // 2.3 查询次数信息（用户在活动上可参与的次数）
    ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

    // 3. 活动动作规则校验 「过滤失败则直接抛异常」
    IActionChain actionChain = defaultActivityChainFactory.openActionChain();
    actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

    // 4. 构建订单聚合对象
    CreateOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

    // 5. 保存订单
    doSaveOrder(createOrderAggregate);

    // 6. 返回单号
    return createOrderAggregate.getActivityOrderEntity().getOrderId();
  }

  protected abstract CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

  protected abstract void doSaveOrder(CreateOrderAggregate createOrderAggregate);
}