package com.qf.shop_kill.service.impl;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import com.qf.shop_kill.dao.IKillDao;
import com.qf.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Primary//表示以该实现类为主
public class KillLeguanServiceimpl implements IKillService {

    @Autowired
    private IKillDao killDao;

    @Override
    public Kill queryOne(Integer id) {
        return killDao.queryOne(id);
    }

    @Override
    @Transactional//sql语句后面跟上for update排它锁之后，这里要加事务
    public int miaosha(Integer id, Integer number, Integer uid) {

        Kill kill = killDao.queryOne(id);

        //尝试扣减库存，直接修改库存
        int i = killDao.updateSave(id, number);
        if(i>0){
            Orders orders=new Orders();
            orders.setOrderid(UUID.randomUUID().toString());//添加商品订单id
            orders.setOprice(kill.getPrice()*number);//计算商品价格
            orders.setUid(uid);//下单人id
            orders.setOrdertime(new Date());//下单时间

            //添加订单
            killDao.addOrder(orders);
        }

        return 0;
    }
}
