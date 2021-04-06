package com.service;

import com.dto.Goods;

import java.util.List;

public interface GoodsService {
    Goods findGoodsById(int id);
    List<Goods> findGoodsByTypeId(int typeId);
    List<Goods> findGoodsByUserId(int userId);
    List<Goods> findAllGoods();
    int insertGoods(Goods goods);
    int deleteGoodsById(int goodsId);
    int deleteManyGoods(int items[]);
}
