package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallLevel;
import com.huobanplus.goodsync.datacenter.repository.LevelRepository;
import com.huobanplus.goodsync.datacenter.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by allan on 5/9/16.
 */
@Service
public class LevelServiceImpl implements LevelService {
    @Autowired
    private LevelRepository levelRepository;

    @Override
    public List<MallLevel> findByCustomerId(int customerId) {
        return levelRepository.findByCustomerId(customerId);
    }
}
