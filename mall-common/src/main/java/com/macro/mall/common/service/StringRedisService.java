package com.macro.mall.common.service;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StringRedisTemplate操作Service类
 * Created by kaomita on 2025年6月4日
 * */
public interface StringRedisService {
    /**
     * 添加hash属性
     * */
    void hSet(String key, HashMap<String, String> map);

    Object hGet(String key, String hashKey);

    /**
     * 删除
     * */
    void hDel(String key);

    /**
     * 获取信息
     * */
    Map<Object, Object> getAll(String key);

    /**
     * 查询数据
     * */
    Object get(String key, String hashKey);

    /**
     * 执行脚本
     * */
    Long execute(DefaultRedisScript<Long> redisScript, List<String> keyLists, Object... argv);
    
    /**
     * setnx,键不存在则返回 true，否则返回 false
     * */
    Boolean setnx(String key, Long expireTime);
}
