package com.qf.shop_kill.service.impl;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import com.qf.shop_kill.dao.IKillDao;
import com.qf.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class KillServiceimpl implements IKillService {

    @Autowired
    private IKillDao killDao;

    @Override
    public Kill queryOne(Integer id) {
        return killDao.queryOne(id);
    }

    @Override
    @Transactional//sql语句后面跟上for update排它锁之后，这里要加事务
    public int miaosha(Integer id, Integer number, Integer uid) {

        //先查询商品库存
       /* Kill kill = killDao.queryOne(id);
        if(kill.getSave()>=number){
            //如果库存大于抢购数量，那么就修改库存并下单
            killDao.updateSave(id,number);

            Orders orders=new Orders();
            orders.setOrderid(UUID.randomUUID().toString());//添加商品订单id
            orders.setOprice(kill.getPrice()*number);//计算商品价格
            orders.setUid(uid);//下单人id
            orders.setOrdertime(new Date());//下单时间

            //添加订单
            killDao.addOrder(orders);


        }*/
        return 0;
    }
}
