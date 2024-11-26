package cn.practice.infrastructure.dao.po;

import lombok.Data;
import java.util.Date;

/**
 * @author yan
 * @description 抽奖策略
 * @create 2024-11-26
 */
@Data
public class Strategy {

  /** 自增id */
  private Long id;
  /** 抽奖策略id */
  private Long strategyId;
  /** 抽奖策略描述 */
  private String strategyDesc;
  /** 创建时间 */
  private Date createTime;
  /** 更新时间 */
  private Date updateTime;
}
