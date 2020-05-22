package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<Goods> queryAll() {

        System.out.println("调用了实现类");
        return goodsDao.queryAll();
    }

    @Override
    public Goods goodsAdd(Goods goods) {
        int i = goodsDao.goodsAdd(goods);
        return goods;
    }

    @Override
    public List<Goods> queryNewAll() {


        return goodsDao.queryNewAll();
    }

    @Override
    public Goods queryById(Integer id) {
        return goodsDao.queryById(id);
    }
}
