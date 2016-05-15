package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallLevel;
import com.huobanplus.goodsync.datacenter.repository.LevelRepository;
import com.huobanplus.goodsync.datacenter.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MallLevel> findByCustomerIdWithOrder(int customerId) {
        List<MallLevel> levels = findByCustomerId(customerId);

        List<MallLevel> results = new ArrayList<>();
        List<MallLevel> userLevelList = levels.stream().filter(level -> level.getType() == 0).collect(Collectors.toList());
        List<MallLevel> buddyLevelList = levels.stream().filter(level -> level.getType() == 1).collect(Collectors.toList());
        results.addAll(userLevelList);
        results.addAll(buddyLevelList);
        return results;
    }
}
