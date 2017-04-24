package com.pku.system.util;

import com.pku.system.controller.NewWebSocket;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangdongyu on 2017/4/24.
 */
@Component
public class TimerTask {
    @Autowired
    DeviceInfoService deviceInfoService;
    List<DeviceInfo> deviceInfoList;
    DealMessage dealMessage = new DealMessage();

    public static List<WSocketMessage> messageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<WSocketMessage>();

    /**
     * 每天0点启动任务
     */
    @Scheduled(cron = "0 0 0 ? * *")
    public void clear()
    {
        NewWebSocket.wSocketMessageListCenter.clear();
        System.out.println("清空消息中心！");
    }

    /**
     * 每8个小时启动任务
     */
    @Scheduled(cron = "0 0 */8 * * ?")
    public void detect()
    {
        NewWebSocket nbs = new NewWebSocket();
        nbs.sendMessageToAll(Constant.DETECTONLINE);//广播

        deviceInfoList = deviceInfoService.getAllDeviceInfoStatus();

        for(int i=0;i<deviceInfoList.size();i++){
            dealMessage.addMessageList(deviceInfoList.get(i).getRaspberryCode(),deviceInfoList.get(i).getBuildingNum()+deviceInfoList.get(i).getClassroomNum(),messageList,deviceInfoList.get(i),messageListCenter);
        }

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String curtime=format.format(date);
        long curTime = Long.parseLong(curtime);

        for(int j=0;j<deviceInfoList.size();j++){//每个树莓派
            for(int i=0;i<messageList.size();i++){
                if(messageList.get(i).getOwnId().equals(deviceInfoList.get(j).getRaspberryCode()))
                {
                    long timeDelay = curTime-Long.parseLong(messageList.get(i).getNowTime());

                    if(timeDelay<60){
                        System.out.println("第一条记录未到时间");
                        break;
                    }else if(deviceInfoList.get(j).getRaspberryStatus() == 0){
                        System.out.println("树莓派已离线");
                        break;
                    }
                    else{
                        dealMessage.addMessageList("offline","all","树莓派离线",messageList.get(i).getOwnId(),deviceInfoList.get(j).getBuildingNum()+deviceInfoList.get(j).getClassroomNum(),NewWebSocket.wSocketMessageList,deviceInfoList.get(j),NewWebSocket.wSocketMessageListCenter);
                        deviceInfoList.get(j).setRaspberryStatus(0);
                        deviceInfoList.get(j).setRaspberryStreamStatus(0);
                        deviceInfoService.updateDeviceInfoStatus(deviceInfoList.get(j));
                        System.out.println("树莓派离线");
                        break;
                    }

                }
            }


        }
    }
}
