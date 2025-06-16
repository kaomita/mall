package com.macro.mall.portal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 优惠卷信息队列消息类
 * 必须要需要无参构造和setter方法
 * */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CouponMessage {
    private Long couponId;
    private Long userId;
    private String nickname;
    private Date time;

}
