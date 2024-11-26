package cn.practice.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import cn.practice.infrastructure.persistent.po.Award;

/**
 * @author yan
 * @description 奖品表DAO
 * @create 2024-11-26
 */
@Mapper
public interface IAwardDao {
  List<Award> queryAwardList();

}
