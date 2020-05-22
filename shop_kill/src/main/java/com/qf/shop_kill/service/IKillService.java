package com.qf.shop_kill.service;

import com.qf.entity.Kill;

public interface IKillService {

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    Kill queryOne(Integer id);

    /**
     * 抢购商品的id、数量、和用户
     * @param id
     * @param number
     * @param uid
     * @return
     */
    int miaosha(Integer id,Integer number,Integer uid);
}
