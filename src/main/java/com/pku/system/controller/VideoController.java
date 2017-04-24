package com.pku.system.controller;

import com.pku.system.model.DeviceInfo;
import com.pku.system.model.PullInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.DeviceInfoService;
import com.pku.system.service.PullInfoService;
import com.pku.system.util.DealMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangdongyu on 2017/4/24.
 */
@Api(value="视频管理",tags = {"视频管理API"},description = "视频流调度")
@RestController
@RequestMapping("/videos")// 通过这里配置使下面的映射都在/videos下
public class VideoController {
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    PullInfoService pullInfoService;

    PullInfo pullInfo = new PullInfo();
    DeviceInfo deviceInfo = new DeviceInfo();
    DeviceInfo deviceInfoPull = new DeviceInfo();
    DealMessage dealMessage = new DealMessage();

    public static List<WSocketMessage> messageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<WSocketMessage>();



}
