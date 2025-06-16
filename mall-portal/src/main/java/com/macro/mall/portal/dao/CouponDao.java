package com.macro.mall.portal.dao;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * 根据优惠卷id减少优惠卷库存
 * */
public interface CouponDao {
    void reduceById(@Param("id") Long id);
}
