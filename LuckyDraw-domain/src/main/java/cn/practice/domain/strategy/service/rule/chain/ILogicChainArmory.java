package cn.practice.domain.strategy.service.rule.chain;

/**
 * @description 责任链装配
 * @create 2024-01-20 11:53
 */
public interface ILogicChainArmory {

  ILogicChain next();

  ILogicChain appendNext(ILogicChain next);

}
