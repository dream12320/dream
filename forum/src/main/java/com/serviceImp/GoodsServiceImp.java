package com.serviceImp;

import com.dto.Goods;
import com.mapper.GoodsMapper;
import com.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImp implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Goods findGoodsById(int id) {
        return goodsMapper.findGoodsById(id);
    }

    @Override
    public List<Goods> findGoodsByTypeId(int typeId) {
        return goodsMapper.findGoodsByTypeId(typeId);
    }

    @Override
    public List<Goods> findGoodsByUserId(int userId) {
        return goodsMapper.findGoodsByUserId(userId);
    }

    @Override
    public List<Goods> findAllGoods() {
        return goodsMapper.findAllGoods();
    }

    @Override
    public int insertGoods(Goods goods) {
        goodsMapper.insertGoods(goods);
        return 0;
    }

    @Override
    public int deleteGoodsById(int goodsId) {
        goodsMapper.deleteGoodsById(goodsId);
        return 0;
    }

    @Override
    public int deleteManyGoods(int[] items) {
        goodsMapper.deleteManyGoods(items);
        return 0;
    }
}
