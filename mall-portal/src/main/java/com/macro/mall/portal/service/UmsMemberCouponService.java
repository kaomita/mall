package com.macro.mall.portal.service;

import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.CouponMessage;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户优惠券管理Service
 * Created by macro on 2018/8/29.
 */
public interface UmsMemberCouponService {
    /**
     * 会员添加优惠券
     */
    void add(Long couponId);

    /**
     * 消息队列处理优惠卷
     * */
    void processCoupon(CouponMessage msg);

    /**
     * 获取优惠券历史列表
     */
    List<SmsCouponHistory> listHistory(Integer useStatus);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartItemList, Integer type);

    /**
     * 获取当前商品相关优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);

    /**
     * 获取用户优惠券列表
     */
    List<SmsCoupon> list(Integer useStatus);
}
