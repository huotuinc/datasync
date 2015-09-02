package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import com.huobanplus.goodsync.datacenter.repository.MallGoodRepository;
import com.huobanplus.goodsync.datacenter.service.MallGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
@Service
@Transactional
public class MallGoodServiceImpl implements MallGoodService {
    @Autowired
    private MallGoodRepository goodRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MallGoodsBean> findByCustomerId(int customerId) {
        return goodRepository.findByCustomerId(customerId);
    }

    @Override
    public MallGoodsBean save(MallGoodsBean goodBean) {
        return goodRepository.save(goodBean);
    }

    @Override
    public void batchSave(List<MallGoodsBean> goodBeans) {
        goodBeans.forEach(goodBean -> {
            goodRepository.save(goodBean);
        });
    }
}
