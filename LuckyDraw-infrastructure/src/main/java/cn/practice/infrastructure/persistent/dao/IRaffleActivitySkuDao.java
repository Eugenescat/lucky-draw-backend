package cn.practice.infrastructure.persistent.dao;

import cn.practice.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Fuzhengwei practice.cn @小傅哥
 * @description 商品sku dao
 * @create 2024-03-16 11:04
 */
@Mapper
public interface IRaffleActivitySkuDao {

  RaffleActivitySku queryActivitySku(Long sku);

}
