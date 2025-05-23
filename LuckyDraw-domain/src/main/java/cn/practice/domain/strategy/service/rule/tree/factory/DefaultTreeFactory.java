package cn.practice.domain.strategy.service.rule.tree.factory;

import cn.practice.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.practice.domain.strategy.model.valobj.RuleTreeVO;
import cn.practice.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.practice.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import cn.practice.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import cn.practice.domain.strategy.service.rule.tree.ILogicTreeNode;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 规则树工厂
 * @create 2024-01-27 11:28
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }

    /**
     * 决策树个动作实习
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }

}
