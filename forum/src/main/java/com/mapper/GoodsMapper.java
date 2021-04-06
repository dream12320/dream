package com.mapper;

import com.dto.Goods;

import java.util.List;

public interface GoodsMapper {

    Goods findGoodsById(int id);
    List<Goods> findGoodsByTypeId(int typeId);
    List<Goods> findGoodsByUserId(int userId);
    List<Goods> findAllGoods();
    int insertGoods(Goods goods);
    int deleteGoodsById(int goodsId);
    int deleteManyGoods(int items[]);
}
