package cn.practice.domain.activity.aggregate;

import cn.practice.domain.activity.model.entity.ActivityAccountEntity;
import cn.practice.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 下单聚合对象
 * @create 2024-03-16 10:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

  /**
   * 活动账户实体
   */
  private ActivityAccountEntity activityAccountEntity;
  /**
   * 活动订单实体
   */
  private ActivityOrderEntity activityOrderEntity;

}

