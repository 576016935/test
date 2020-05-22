package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods> queryAll();

    Goods goodsAdd(Goods goods);

    List<Goods> queryNewAll();

    Goods queryById(Integer id);
}
