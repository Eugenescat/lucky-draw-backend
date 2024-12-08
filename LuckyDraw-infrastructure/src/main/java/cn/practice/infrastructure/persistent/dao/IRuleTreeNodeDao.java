package cn.practice.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import cn.practice.infrastructure.persistent.po.RuleTreeNode;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 规则树节点表DAO
 * @create 2024-02-03 08:43
 */
@Mapper
public interface IRuleTreeNodeDao {

  List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

}
