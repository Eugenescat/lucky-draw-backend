package cn.practice.domain.activity.service.armory;

import java.util.Date;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 活动装配预热
 * @create 2024-03-30 09:09
 */
public interface IActivityArmory {

    boolean assembleActivitySku(Long sku);
    boolean subtractionActivitySkuStock(Long sku, Date endDatetTime);

}

