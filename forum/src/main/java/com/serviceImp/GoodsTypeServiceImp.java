package com.serviceImp;

import com.dto.GoodsType;
import com.mapper.GoodsTypeMapper;
import com.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeServiceImp implements GoodsTypeService {
    @Autowired
    GoodsTypeMapper goodsTypeMapper;


    @Override
    public GoodsType findGoodsTypeById(int id) {
        return goodsTypeMapper.findGoodsTypeById(id);
    }

    @Override
    public GoodsType findGoodsTypeByName(String name) {
        return goodsTypeMapper.findGoodsTypeByName(name);
    }

    @Override
    public List<GoodsType> findAllGoodsType() {
        return goodsTypeMapper.findAllGoodsType();
    }

    @Override
    public int insertGoodsType(GoodsType goodsType) {
        goodsTypeMapper.insertGoodsType(goodsType);
        return 0;
    }

    @Override
    public int deleteGoodsTypeById(int id) {
        goodsTypeMapper.deleteGoodsTypeById(id);
        return 0;
    }

    @Override
    public int updateGoodsType(GoodsType goodsType) {
        goodsTypeMapper.updateGoodsType(goodsType);
        return 0;
    }
}
