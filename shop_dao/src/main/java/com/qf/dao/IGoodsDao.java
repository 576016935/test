package com.qf.dao;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsDao {

    List<Goods> queryAll();

    int goodsAdd(Goods goods);

    List<Goods> queryNewAll();

    Goods queryById(Integer id);
}
