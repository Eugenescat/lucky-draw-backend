package cn.practice.test.infrastructure;

import cn.practice.domain.strategy.model.valobj.RuleTreeVO;
import cn.practice.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.practice.domain.strategy.repository.IStrategyRepository;
import com.alibaba.fastjson2.JSON;

import cn.practice.infrastructure.persistent.redis.IRedisService;
import cn.practice.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 策略仓储测试
 * @create 2024-02-03 09:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;

    @Resource
    private IRedisService redisService;

    @Test
    public void queryRuleTreeVOByTreeId() {
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果：{}", JSON.toJSONString(ruleTreeVO));
    }

    @Test
    public void test_cacheStrategyAwardCount() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + "300001" + Constants.UNDERLINE + "102";
        strategyRepository.cacheStrategyAwardCount(cacheKey, 10);
    }

    @Test
    public void test_subtractionAwardStock() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + "300001" + Constants.UNDERLINE + "102";
        strategyRepository.subtractionAwardStock(cacheKey);
    }

    @Test
    public void test_getValue() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + "300001" + Constants.UNDERLINE + "102";
        Integer value = redisService.getValue(cacheKey);
        log.info("测试结果：{}", value);
    }

    @Test
    public void test_queue() throws InterruptedException {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<StrategyAwardStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(StrategyAwardStockKeyVO.builder()
                .strategyId(100001L)
                .awardId(102)
                .build(), 3, TimeUnit.SECONDS);

        new CountDownLatch(1).await();
    }


}
