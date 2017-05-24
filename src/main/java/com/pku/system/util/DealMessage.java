package com.pku.system.util;

import com.pku.system.model.Camera;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DealMessage {
    /**
     * 添加消息到列表中
     * @param ownId
     * @param buildClass
     * @param messageList
     * @param deviceInfo
     * @param wSocketMessageListCenter
     */
    public  void addMessageList(String ownId, String buildClass, List<WSocketMessage> messageList, DeviceInfo deviceInfo, List<WSocketMessage> wSocketMessageListCenter){
        WSocketMessage wSocketMessage = new WSocketMessage();
        //获取当前系统时间
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String time=format.format(date);

        wSocketMessage.setNowTime(time);
        wSocketMessage.setOwnId(ownId);
        wSocketMessage.setBuildClass(buildClass);
        wSocketMessage.setDeviceInfo(deviceInfo);
        //将获取的信息加入列表
        messageList.add(wSocketMessage);
        wSocketMessageListCenter.add(wSocketMessage);
    }

    /**
     * 添加消息到列表中
     * @param judge
     * @param tab
     * @param message
     * @param ownId
     * @param buildClass
     * @param wSocketMessageList
     * @param deviceInfo
     * @param wSocketMessageListCenter
     */
    public void addMessageList(String judge,String tab,String message,String ownId,String buildClass,List<WSocketMessage> wSocketMessageList,DeviceInfo deviceInfo,List<WSocketMessage> wSocketMessageListCenter){
        WSocketMessage wSocketMessage = new WSocketMessage();
        wSocketMessage.setMessage(message);
        //获取当前系统时间
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);

        wSocketMessage.setNowTime(time);
        wSocketMessage.setOwnId(ownId);
        wSocketMessage.setBuildClass(buildClass);
        wSocketMessage.setJudge(judge);
        wSocketMessage.setDeviceInfo(deviceInfo);
        wSocketMessage.setTab(tab);
        //将获取的信息加入列表
        wSocketMessageList.add(wSocketMessage);
        wSocketMessageListCenter.add(wSocketMessage);
    }

    /**
     * 处理设备管理返回前端的消息以及数据库
     * @param msgp
     * @param deviceInfo
     * @param msg
     * @param ownId
     * @param wSocketMessageList
     * @param dic
     * @param wSocketMessageListCenter
     */
    public void deviceOperation(String[] msgp, DeviceInfo deviceInfo, String msg, String ownId, List<WSocketMessage> wSocketMessageList, Map<String, String> dic, List<WSocketMessage> wSocketMessageListCenter,List<Camera> cameraList){
        //处理设备开关
        if(msgp[0].equals("success")){
            if(msgp[2].equals("all")){//操作全部
                addMessageList("success","device",msgp[1].equals("open")?"开启所有成功":"关闭所有成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setCameraStatus(msgp[1].equals("open")?1:0);
                deviceInfo.setComputerStatus(msgp[1].equals("open")?1:0);
                deviceInfo.setProjectorStatus(msgp[1].equals("open")?1:0);
                for(Camera camera: cameraList){
                    camera.setCameraStatus(msgp[1].equals("open")?1:0);
                }
            }else if(msgp[2].equals("camera")){//处理摄像头开关
                int caid = Integer.parseInt(msgp[3])-1;
                Camera camera = cameraList.get(caid);//获得操作的具体camera
                camera.setCameraStatus(msgp[1].equals("open")?1:0);
                addMessageList("success","device",msgp[1].equals("open")?"开启camera成功":"关闭camera成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
            }else{//处理电脑，投影仪开关
                String device = msgp[2].substring(0,1).toUpperCase()+msgp[2].substring(1);//首字母大写
                try {
                    Method method = deviceInfo.getClass().getMethod("set"+device+"Status",int.class);//反射机制
                    method.invoke(deviceInfo,msgp[1].equals("open")?1:0);//1在线，0离线
                    addMessageList("success","device",msgp[1].equals("open")?"开启"+dic.get(msgp[2])+"成功":"关闭"+dic.get(msgp[2])+"成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }else if(msgp[0].equals("fail")){

            if(msg.contains("singlechip")){//一旦收到操作单片机失败，即把其状态置为异常
                deviceInfo.setSinglechipStatus(2);
                addMessageList("fail","device","单片机异常",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
            }

            String returnM = msgp[1].equals("open")?"开启所有失败 ":"关闭所有失败 ";

            if(msgp[2].equals("all")){
                if(!msg.contains("computer")){
                    deviceInfo.setComputerStatus(msgp[1].equals("open")?1:0);
                    returnM +=  msgp[1].equals("open")?"开启电脑成功":"关闭电脑成功";
                }else if(msg.contains("computer")){
                    deviceInfo.setComputerStatus(2);
                }

                if(!msg.contains("projector")){
                    deviceInfo.setProjectorStatus(msgp[1].equals("open")?1:0);
                    returnM += msgp[1].equals("open")?"开启投影仪成功":"关闭投影仪成功";
                }else if(msg.contains("projector")){
                    deviceInfo.setProjectorStatus(2);
                }

                if(!msg.contains("camera")){
                    deviceInfo.setCameraStatus(msgp[1].equals("open")?1:0);
                    for(Camera camera: cameraList){
                        camera.setCameraStatus(msgp[1].equals("open")?1:0);
                    }
                    returnM += msgp[1].equals("open")?"开启全部摄像头成功":"关闭全部摄像头成功";
                }else if(msg.contains("camera")){//多个摄像头失败,摄像头默认拼在末尾
                    int index = 0;
                    String total[] = new String[cameraList.size()];//总共有多少个摄像头

                    //获得camera的下标
                    for(int i=0;i<msgp.length;i++){
                        if(msgp[i].equals("camera")){
                            index = i;
                        }
                    }

                    String[] caid = new String[msgp.length-index-1];
                    String[] result = new String[total.length-caid.length];

                    //获得失败的camera的编号
                    for(int i= index ;i<caid.length+index;i++){
                        caid[msgp.length-i-2] = Integer.toString(Integer.parseInt(msgp[i+1])-1);
                    }

                    for(int i=0;i<caid.length;i++){
                        Camera camera = cameraList.get(Integer.parseInt(caid[i]));//获得操作的具体camera
                        camera.setCameraStatus(2);
                    }

                    //total初始化
                    for(int i=0;i<total.length;i++){
                        total[i] = Integer.toString(i);
                    }

                    //求两个数组的差集
                    if(total.length != caid.length){
                        result = Array.minus(caid,total);
                    }

                    for(int i=0;i<result.length;i++){
                        Camera camera = cameraList.get(Integer.parseInt(result[i]));//获得操作的具体camera
                        camera.setCameraStatus(msgp[1].equals("open")?1:0);
                        returnM += msgp[1].equals("open")?"开启摄像头成功":"关闭摄像头成功";
                    }


                }

                addMessageList("fail","device",returnM,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);

            }else{//单一操作，即操作单个设备
                String device = msgp[2].substring(0,1).toUpperCase()+msgp[2].substring(1);//首字母大写

                if(device.equals("Camera")){
                    int caid = Integer.parseInt(msgp[3])-1;
                    Camera camera = cameraList.get(caid);//获得操作的具体camera
                    camera.setCameraStatus(2);
                    addMessageList("fail","device",msgp[1].equals("open")?"开启camera成功":"关闭camera成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);

                }else{
                    try {
                        Method method = deviceInfo.getClass().getMethod("set"+device+"Status",int.class);
                        method.invoke(deviceInfo,2);
                        addMessageList("fail","device",msgp[1].equals("open")?"开启"+dic.get(msgp[2])+"失败":"关闭"+dic.get(msgp[2])+"失败",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }


            }
        }
    }

    /**
     * 处理视频管理返回前端的消息以及数据库的处理
     * @param msgp
     * @param deviceInfo
     * @param msg
     * @param ownId
     * @param wSocketMessageList
     * @param wSocketMessageListCenter
     */
    public void streamOperation(String[] msgp, DeviceInfo deviceInfo, String msg, String ownId, List<WSocketMessage> wSocketMessageList, List<WSocketMessage> wSocketMessageListCenter){
        //处理推拉流
        if(msgp[0].equals("start")){

            if(msg.contains("broadcast")){//start_push_broadcast
                addMessageList("success","video","开始广播",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(5);//正在广播
            }else{
                //start_push,start_pull
                addMessageList("success","video",msg.contains("push")?"开始推流":"开始拉流",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(msg.contains("push")?3:4);//开始推拉流，则把树莓派状态置为正在推流或正在拉流
            }
        }else if(msgp[0].equals("success")){
            if(msg.contains("broadcast")){//success_push_boradcast,success_stop_push_boradcast
                addMessageList("success","video",msg.contains("stop")?"广播结束":"广播成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(1);
            }
            if(msgp.length==2){//success_push,success_pull
                addMessageList("success","video",msg.contains("push")?"推流成功":"拉流成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(1);//成功推拉流，则把树莓派状态置为空闲（在线）
            }else if(msg.contains("stop")){//success_stop_push,success_stop_pull
                addMessageList("success","video",msg.contains("push")?"推流结束":"拉流结束",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);

                deviceInfo.setRaspberryStreamStatus(1);//成功推拉流，则把树莓派状态置为空闲（在线）
            }

        }else if(msgp[0].equals("fail")){
            //推流，广播，拉流判断
            if(msg.contains("stop")){//fail_stop_push,fail_stop_pull,fail_stop_push_broadcast
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILSTOPPUSHBROADCAST,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(5);//停止广播失败，则把树莓派状态置为正在广播
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILSTOPPUSH:Constant.FAILSTOPPULL,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(msg.contains("push")?3:4);//停止推拉流失败，则把树莓派状态置为正在推流或正在拉流
                }
            }

            if(msg.contains("disconnectServer")){
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILPUSHBROADCASTDISCONNECTSERVER,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILPUSHDISCONNECTSERVER:Constant.FAILPULLDISCONNECTSERVER,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);//推拉流失败，在线
                }
            }
            if(msg.contains("unknown")){
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILPUSHBROADCASTUNKNOWN,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILPUSHUNKNOWN:Constant.FAILPULLUNKNOWN,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);//推拉流失败
                }
            }
            if(msg.contains("server")){
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILPUSHBROADCASTSERVER,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILPUSHSERVER:Constant.FAILPULLSERVER,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);//推拉流失败
                }
            }
            if(msg.contains("weakConnect")){
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILPUSHBROADCASTWEAKCONNECT,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILPUSHWEAKCONNECT:Constant.FAILPULLWEAKCONNECT,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);//推拉流失败
                }
            }
            if(msg.contains("timeout")){
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILPUSHBROADCASTTIMEOUT,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILPUSHTIMEOUT:Constant.FAILPULLTIMEOUT,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    deviceInfo.setRaspberryStreamStatus(1);//推拉流失败
                }
            }

            //推流和广播的判断
            if(msg.contains("connectServer")){

                addMessageList("fail","video",msg.contains("broadcast")?Constant.FAILPUSHBROADCASTCONNECTSERVER:Constant.FAILPUSHCONNECTSERVER,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(1);//推流失败

            }
            if(msg.contains("openMIC")){
                addMessageList("fail","video",msg.contains("broadcast")?Constant.FAILPUSHBROADCASTOPENMIC:Constant.FAILPUSHOPENMIC,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(1);//推流失败

            }
            if(msg.contains("openCamera")){
                addMessageList("fail","video",msg.contains("broadcast")?Constant.FAILPUSHBROADCASTOPENCAMERA:Constant.FAILPUSHOPENCAMERA,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(1);//推流失败

            }
            if(msg.contains("prepareRecorder")){
                addMessageList("fail","video",msg.contains("broadcast")?Constant.FAILPUSHBROADCASTPREPARERECORDER:Constant.FAILPUSHPREPARERECORDER,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                deviceInfo.setRaspberryStreamStatus(1);//推流失败

            }

        }
    }


}
