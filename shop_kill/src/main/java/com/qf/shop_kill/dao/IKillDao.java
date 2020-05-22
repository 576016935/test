package com.qf.shop_kill.dao;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IKillDao {

    Kill queryOne(Integer id);//根据商品id查询商品信息

    int updateSave(@Param("id") Integer id,@Param("number") Integer number);//抢购商品后修改商品库存的数量

    int addOrder(Orders orders);
}
