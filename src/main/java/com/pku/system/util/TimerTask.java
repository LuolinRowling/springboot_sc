package com.pku.system.util;

import com.pku.system.controller.NewWebSocket;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TimerTask {

    public static Map<String,String> messageMap = new HashMap<String,String>();
    public Map<String,String> keyMap = new HashMap<String,String>();

    public static List<WSocketMessage> messageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<WSocketMessage>();

    @Autowired
    DeviceInfoService deviceInfoService;

    List<DeviceInfo> deviceInfoList;
    DealMessage dealMessage = new DealMessage();

    Time time = new Time();

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
     * 每30min启动任务
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void detect()
    {
        String id = time.getCurrentTime();

        NewWebSocket nbs = new NewWebSocket();

        deviceInfoList = deviceInfoService.getAllDeviceInfoStatus();

        for(int i=0;i<deviceInfoList.size();i++){
            nbs.sendDeviceMessageToOne(id+i,deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum(), Constant.DETECTONLINE);
            keyMap.put(id+i,deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum());
            dealMessage.addMessageList(deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum(),deviceInfoList.get(i).getBuildingNum()+deviceInfoList.get(i).getClassroomNum(),messageList,deviceInfoList.get(i),messageListCenter);
        }

        try {
            Thread.sleep(Constant.MESSAGETIMEOUTBROADCAST);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0;i<deviceInfoList.size();i++){
            if(messageMap.get(id+i) != null){
                String[] bc = keyMap.get(id+i).split("_");
                DeviceInfo deviceInfoCur = deviceInfoService.selectByBuildingClassroom(bc[0],bc[1]);

                deviceInfoCur.setRaspberryStatus(1);
                deviceInfoCur.setRaspberryStreamStatus(1);

                System.out.println("树莓派在线");

                deviceInfoService.updateDeviceInfoStatus(deviceInfoCur);

                keyMap.remove(id+i);//删除已处理的教学楼教室
            }
        }

        //对未返回消息的教学楼教室作离线处理
        for(int i=0;i<deviceInfoList.size();i++){
            if(keyMap.get(id+i) !=null){
                String[] bc = keyMap.get(id+i).split("_");
                DeviceInfo deviceInfoCur = deviceInfoService.selectByBuildingClassroom(bc[0],bc[1]);

                deviceInfoCur.setRaspberryStatus(2);
                deviceInfoCur.setRaspberryStreamStatus(2);

                System.out.println("树莓派异常");

                deviceInfoService.updateDeviceInfoStatus(deviceInfoCur);

            }

        }
    }
}
