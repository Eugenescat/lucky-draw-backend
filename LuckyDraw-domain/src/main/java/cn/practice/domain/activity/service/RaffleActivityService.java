package cn.practice.domain.activity.service;

import cn.practice.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 抽奖活动服务
 * @create 2024-03-16 08:41
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity {

  public RaffleActivityService(IActivityRepository activityRepository) {
    super(activityRepository);
  }

}
