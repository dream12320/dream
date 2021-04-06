package com.mapper;

import com.dto.GoodsType;

import java.util.List;

public interface GoodsTypeMapper {

    GoodsType findGoodsTypeById(int id);
    GoodsType findGoodsTypeByName(String name);
    List<GoodsType> findAllGoodsType();
    int insertGoodsType(GoodsType goodsType);
    int deleteGoodsTypeById(int id);
    int updateGoodsType(GoodsType goodsType);
}
