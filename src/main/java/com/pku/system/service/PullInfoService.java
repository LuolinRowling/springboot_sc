package com.pku.system.service;

import com.pku.system.model.PullInfo;

/**
 * Created by jiangdongyu on 2017/4/22.
 */
public interface PullInfoService {
    /**
     * 查询
     * @param id
     * @return
     */
    public PullInfo selectById(int id);

    /**
     * 增加
     * @param pullInfo
     */
    public void addPullInfo(PullInfo pullInfo);

    /**
     * 修改
     * @param pullInfo
     */
    public void updatePullInfo(PullInfo pullInfo);
}
