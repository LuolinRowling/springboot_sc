package com.pku.system.service.impl;

import com.pku.system.dao.PullInfoDao;
import com.pku.system.model.PullInfo;
import com.pku.system.service.PullInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangdongyu on 2017/4/22.
 */
@Service
public class PullInfoServiceImpl implements PullInfoService {
    @Autowired
    PullInfoDao pullInfoDao;

    @Override
    public PullInfo selectById(int id) {
        return pullInfoDao.selectById(id);
    }

    @Override
    public void addPullInfo(PullInfo pullInfo) {
        pullInfoDao.addPullInfo(pullInfo);

    }

    @Override
    public void updatePullInfo(PullInfo pullInfo) {
        pullInfoDao.updatePullInfo(pullInfo);

    }
}
