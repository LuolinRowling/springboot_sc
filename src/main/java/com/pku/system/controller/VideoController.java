package com.pku.system.controller;

import com.pku.system.model.Camera;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.PullInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.CameraService;
import com.pku.system.service.DeviceInfoService;
import com.pku.system.service.PullInfoService;
import com.pku.system.util.Constant;
import com.pku.system.util.DealMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    CameraService cameraService;

    PullInfo pullInfo = new PullInfo();
    DeviceInfo deviceInfo = new DeviceInfo();
    DeviceInfo deviceInfoPull = new DeviceInfo();
    DealMessage dealMessage = new DealMessage();

    public static List<WSocketMessage> messageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<WSocketMessage>();

    @ApiOperation(value = "获得视频推拉流列表", notes = "获得视频推拉流列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllAssignDevice() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        List<DeviceInfo> raspberryStreamStatusList = deviceInfoService.getRaspberryStreamStatus();

        jsonData.put("deviceStatusList",raspberryStreamStatusList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 推流，停止拉流，广播
     * @param did
     * @param operation operation:start_push,stop_push,stop_pull,start_broadcast,stop_broadcast
     * @return
     */
    @ApiOperation(value = "根据id发送单个教室推拉流消息", notes = "根据id发送单个教室推拉流消息notes", produces = "application/json")
    @RequestMapping(value="/{did}", method= RequestMethod.GET)
    public String ajaxEditStreamStatus(@PathVariable(value="did")int did,
                                       @RequestParam(value="operation")String operation){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        try{
            System.out.println(did+" "+operation);
            deviceInfo = deviceInfoService.selectById(did);
            List<DeviceInfo> deviceInfoStreamList = deviceInfoService.selectAllExceptId(did);

            NewWebSocket nbs = new NewWebSocket();

            //dealMessage.addMessageList(deviceInfo.getRaspberryCode(),deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),messageList,deviceInfo,messageListCenter);

            if(operation.contains("push")){
                //nbs.sendMessageToOne(deviceInfo.getRaspberryCode(),Constant.PUSHHEADER+deviceInfo.getRaspberryCode()+Constant.PUSHFOOTER,
                //operation.equals("start_push")?Constant.STARTPUSH:Constant.STOPTPUSH);
                nbs.sendMessageToOne(deviceInfo.getRaspberryCode(),Constant.STREAMADDRESS+deviceInfo.getRaspberryCode(),
                        operation.equals("start_push")?Constant.STARTPUSH:Constant.STOPTPUSH);
            }else if(operation.contains("pull")) {//停止拉流
                Thread.sleep(6 * 1000);//睡眠3s

                nbs.sendMessageToOne(deviceInfo.getRaspberryCode(), "serverAddress", Constant.STOPTPULL);
            }else{//广播，实则为一个教室推流，其余教室拉流
                //nbs.sendMessageToOne(deviceInfo.getRaspberryCode(),Constant.PUSHHEADER+deviceInfo.getRaspberryCode()+Constant.PUSHFOOTER,
                //operation.equals("start_broadcast")?Constant.STARTPUSHBROADCAST:Constant.STOPTPUSHBROADCAST);//选择广播的教室推流
                nbs.sendMessageToOne(deviceInfo.getRaspberryCode(),Constant.STREAMADDRESS+deviceInfo.getRaspberryCode(),
                        operation.equals("start_broadcast")?Constant.STARTPUSHBROADCAST:Constant.STOPTPUSHBROADCAST);//选择广播的教室推流

                //是否需要睡眠时间，需要和安卓组商量
                Thread.sleep(6*1000);//睡眠3s

                for(int i=0;i<deviceInfoStreamList.size();i++){
                    //nbs.sendMessageToOne(deviceInfoStreamList.get(i).getRaspberryCode(),Constant.PULLHEADER+deviceInfo.getRaspberryCode()+Constant.PULLFOOTER,
                    //operation.equals("start_broadcast")?Constant.STARTPULL:Constant.STOPTPULL);//其余教室拉流
                    nbs.sendMessageToOne(deviceInfoStreamList.get(i).getRaspberryCode(),Constant.STREAMADDRESS+deviceInfo.getRaspberryCode(),
                            operation.equals("start_broadcast")?Constant.STARTPULL:Constant.STOPTPULL);//其余教室拉流

                    PullInfo pullInfoOld = pullInfoService.selectById(deviceInfoStreamList.get(i).getId());
                    if(pullInfoOld!=null){//数据库中已经存在该条记录，执行更新操作
                        pullInfoOld.setBuildingNum(deviceInfo.getBuildingNum());
                        pullInfoOld.setClassroomNum(deviceInfo.getClassroomNum());
                        pullInfoService.updatePullInfo(pullInfoOld);
                    }else{
                        pullInfo.setId(deviceInfoStreamList.get(i).getId());
                        pullInfo.setBuildingNum(deviceInfo.getBuildingNum());
                        pullInfo.setClassroomNum(deviceInfo.getClassroomNum());
                        pullInfoService.addPullInfo(pullInfo);
                    }

                    //dealMessage.addMessageList(deviceInfoStreamList.get(i).getRaspberryCode(),deviceInfoStreamList.get(i).getBuildingNum()+deviceInfoStreamList.get(i).getClassroomNum(),messageList,deviceInfo,messageListCenter);
                }

            }

            //修改成功
            jsonData.put("judge","0");
        }catch (DataAccessException e){
            //修改失败
            jsonData.put("judge","-9");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 获得推流的教学楼，以及对应第一个教学楼下的所有教室
     * @return
     */
    @ApiOperation(value = "获得推流的教学楼", notes = "获得推流的教学楼notes", produces = "application/json")
    @RequestMapping(value="/pushBuilding", method= RequestMethod.GET)
    public String ajaxGetPushBuilding(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();


        List<DeviceInfo> pushList = deviceInfoService.getAllPushBuildingList(3);
        jsonData.put("buildingList",pushList);


        List<DeviceInfo> classroomList = deviceInfoService.getAllPushClassroomList(pushList.get(0).getBuildingNum(),3);
        jsonData.put("classroomList",classroomList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 根据教学楼获得教室信息
     * @param name
     * @return
     */
    @ApiOperation(value = "根据教学楼获得教室信息", notes = "根据教学楼获得教室信息notes", produces = "application/json")
    @RequestMapping(value="/classroomByBuilding", method= RequestMethod.GET)
    public String ajaxGetClassroomByBuilding(@RequestParam(value="name")String name){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","0000");
        jsonObject.put("msg","调用成功");
        JSONObject jsonData = new JSONObject();

        System.out.println(name);
        List<DeviceInfo> classroomList = deviceInfoService.getAllPushClassroomList(name,3);
        jsonData.put("classroomList",classroomList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 开始拉流
     * @param did
     * @param buildingNum
     * @param classroomNum
     * @return
     */
    @ApiOperation(value = "开始拉流", notes = "开始拉流notes", produces = "application/json")
    @RequestMapping(value="/pull/{did}", method= RequestMethod.GET)
    public String ajaxPullStreamStatus(@PathVariable(value="did")int did,
                                       @RequestParam(value="buildingNum")String buildingNum,
                                       @RequestParam(value="classroomNum")String classroomNum){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        //修改成功
        jsonData.put("judge","0");
        try{
            System.out.println(did+" "+buildingNum+" "+classroomNum);
            deviceInfoPull = deviceInfoService.selectById(did);
            DeviceInfo deviceInfoPush = deviceInfoService.selectByBuildingClassroom(buildingNum,classroomNum);

            NewWebSocket nbs = new NewWebSocket();

            dealMessage.addMessageList(deviceInfoPull.getRaspberryCode(),deviceInfoPull.getBuildingNum()+deviceInfoPull.getClassroomNum(),messageList,deviceInfoPull,messageListCenter);

            Thread.sleep(6*1000);//睡眠3s
            //nbs.sendMessageToOne(deviceInfoPull.getRaspberryCode(),Constant.PULLHEADER+deviceInfoPush.getRaspberryCode()+Constant.PULLFOOTER,Constant.STARTPULL);

            nbs.sendMessageToOne(deviceInfoPull.getRaspberryCode(),Constant.STREAMADDRESS+deviceInfoPush.getRaspberryCode(),Constant.STARTPULL);

            PullInfo pullInfoOld = pullInfoService.selectById(did);
            if(pullInfoOld!=null){//数据库中已经存在该条记录，执行更新操作
                pullInfoOld.setBuildingNum(buildingNum);
                pullInfoOld.setClassroomNum(classroomNum);
                pullInfoService.updatePullInfo(pullInfoOld);
            }else{
                pullInfo.setId(did);
                pullInfo.setBuildingNum(buildingNum);
                pullInfo.setClassroomNum(classroomNum);
                pullInfoService.addPullInfo(pullInfo);
            }

            //修改成功
            jsonData.put("judge","0");
        }catch (DataAccessException e){
            //修改失败
            jsonData.put("judge","-9");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 多个教室拉流，显示树形控件数据
     * @return
     */
    @ApiOperation(value = "多个教室拉流，显示树形控件数据", notes = "多个教室拉流，显示树形控件数据notes", produces = "application/json")
    @RequestMapping(value="/pullStreamTree", method= RequestMethod.GET)
    public String ajaxMultiPullStreamTree(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        List<DeviceInfo> pushList = deviceInfoService.getAllPushBuildingList(1);

        for(int i=0;i<pushList.size();i++){
            List<DeviceInfo> classroomList = deviceInfoService.getAllPushClassroomList(pushList.get(i).getBuildingNum(),1);
            for(int j=0;j<classroomList.size();j++){
                jsonData.put("buildingNum",pushList.get(i).getBuildingNum());
                jsonData.put("classroomNum",classroomList.get(j).getClassroomNum());
                jsonArray.add(jsonData);
            }

        }

        jsonObject.put("data",jsonArray);
        return jsonObject.toString();
    }

    /**
     * 多教室拉流
     * @return
     */
    @ApiOperation(value = "多个教室拉流", notes = "多个教室拉流notes", produces = "application/json")
    @RequestMapping(value="/multiplePullStreamStatus", method= RequestMethod.GET, consumes = "application/json")
    public String ajaxMultiplePullStreamStatus(@RequestParam(value="buildingNum")String buildingNum,
                                        @RequestParam(value="classroomNum")String classroomNum,
                                        @RequestParam(value="pullList[]")String[] pullList){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        //修改成功
        jsonData.put("judge","0");
        System.out.println(pullList.toString()+" "+buildingNum+" "+classroomNum);
        DeviceInfo deviceInfoPush = deviceInfoService.selectByBuildingClassroom(buildingNum,classroomNum);//推流的信息
        for(int i=0;i<pullList.length;i++){
            String res = pullList[i];
            String[] pullInfoArray = res.split(";");
            deviceInfoPull = deviceInfoService.selectByBuildingClassroom(pullInfoArray[0],pullInfoArray[1]);//获得选择拉流的教室信息

            NewWebSocket nbs = new NewWebSocket();

            //dealMessage.addMessageList(deviceInfoPull.getRaspberryCode(),deviceInfoPull.getBuildingNum()+deviceInfoPull.getClassroomNum(),messageList,deviceInfoPull,messageListCenter);

            try {
                Thread.sleep(6*1000);//睡眠3s
                //nbs.sendMessageToOne(deviceInfoPull.getRaspberryCode(),Constant.PULLHEADER+deviceInfoPush.getRaspberryCode()+Constant.PULLFOOTER,Constant.STARTPULL);
                nbs.sendMessageToOne(deviceInfoPull.getRaspberryCode(), Constant.STREAMADDRESS+deviceInfoPush.getRaspberryCode(),Constant.STARTPULL);

                PullInfo pullInfoOld = pullInfoService.selectById(deviceInfoPull.getId());
                if(pullInfoOld!=null){//数据库中已经存在该条记录，执行更新操作
                    pullInfoOld.setBuildingNum(buildingNum);
                    pullInfoOld.setClassroomNum(classroomNum);
                    pullInfoService.updatePullInfo(pullInfoOld);
                }else{
                    pullInfo.setId(deviceInfoPull.getId());
                    pullInfo.setBuildingNum(buildingNum);
                    pullInfo.setClassroomNum(classroomNum);
                    pullInfoService.addPullInfo(pullInfo);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 根据did获取教室设备信息
     * @param did
     */
    @ApiOperation(value = "根据did获取教室设备信息", notes = "根据did获取教室设备信息notes", produces = "application/json")
    @RequestMapping(value="/classroomInfo/{did}", method= RequestMethod.GET)
    public @ResponseBody String ajaxGetAddClassroomInfo(@PathVariable(value="did")int did){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        System.out.println(did);

        DeviceInfo deviceInfo = deviceInfoService.selectById(did);

        List<Camera> cameraList = cameraService.selectByDeviceId(deviceInfo.getId());

        deviceInfo.setCameraList(cameraList);

        if(deviceInfo == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("deviceInfo",deviceInfo);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 摄像头导播
     * @param did
     */
    @ApiOperation(value = "摄像头导播", notes = "摄像头导播notes", produces = "application/json")
    @RequestMapping(value="/camera/{did}", method= RequestMethod.GET)
    public @ResponseBody String cameraPlay(@PathVariable(value="did")int did,
                                           @RequestParam(value="code")int code,
                                           @RequestParam(value="direction")String direction){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        System.out.println(did+" "+code+" "+direction);

        NewWebSocket nbs = new NewWebSocket();

        deviceInfo = deviceInfoService.selectById(did);

        nbs.sendMessageToOne(deviceInfo.getRaspberryCode(),"", direction+"_"+code);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 播放视频，获得推拉流地址
     * @param did
     * @return
     */
    @ApiOperation(value = "播放视频", notes = "播放视频notes", produces = "application/json")
    @RequestMapping(value="/play/{did}", method= RequestMethod.GET)
    public String ajaxGetPullAddress(@PathVariable(value="did")int did,
                                     @RequestParam(value="code")int code){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String address = "";

        System.out.println(did+" "+code);
        DeviceInfo deviceInfoCur = deviceInfoService.selectById(did);
        if(deviceInfoCur.getRaspberryStreamStatus() == 3){
            //address = Constant.PULLHEADER+deviceInfoCur.getRaspberryCode()+Constant.PULLFOOTER;//自己服务器地址
            address = Constant.STREAMADDRESS+deviceInfoCur.getRaspberryCode()+"/"+code;
            //address = addressMap.get(deviceInfoCur.getRaspberryCode()+"_pull");

        }else if(deviceInfoCur.getRaspberryStreamStatus() == 4){
            pullInfo = pullInfoService.selectById(did);
            //获取所选择教学课教室号的设备记录
            deviceInfo = deviceInfoService.selectByBuildingClassroom(pullInfo.getBuildingNum(),pullInfo.getClassroomNum());
            //拼接拉流地址
            //address = Constant.PULLHEADER+deviceInfo.getRaspberryCode()+Constant.PULLFOOTER;
            address = Constant.STREAMADDRESS+deviceInfo.getRaspberryCode()+"/"+code;
            //address = addressMap.get(deviceInfo.getRaspberryCode()+"_pull");
        }
        System.out.println(address);
        jsonData.put("address",address);
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }



}
