package com.pku.system.controller;

import com.pku.system.model.Camera;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.CameraService;
import com.pku.system.service.DeviceInfoService;
import com.pku.system.util.Constant;
import com.pku.system.util.DealMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangdongyu on 2017/4/24.
 */
@Api(value="远程设备管理",tags = {"远程设备管理API"},description = "描述信息")
@RestController
@RequestMapping("/deviceMonitor")// 通过这里配置使下面的映射都在/deviceMonitor下
public class DeviceMonitorController {
    //自动注入业务层的deviceInfoService类
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    CameraService cameraService;

    WSocketMessage wSocketMessage = new WSocketMessage();
    DeviceInfo deviceInfo;
    Camera camera;
    List<DeviceInfo> deviceInfoList;
    DealMessage dealMessage = new DealMessage();

    public static List<WSocketMessage> messageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<WSocketMessage>();

    @ApiOperation(value = "获得设备管理列表", notes = "获得设备管理列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllDeviceMonitor(){
        // 处理"/deviceMonitor/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        List<DeviceInfo> deviceStatusList = deviceInfoService.getAllDeviceInfoStatus();

        for(int i = 0;i < deviceStatusList.size();i++){
            System.out.println(deviceStatusList.get(i).getId());
            List<Camera> cameraList = cameraService.selectByDeviceId(deviceStatusList.get(i).getId());
            deviceStatusList.get(i).setCameraList(cameraList);

            jsonArray.add(deviceStatusList.get(i));
            jsonData.put("deviceStatusList",jsonArray);
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id发送修改单个教室设备状态消息", notes = "根据id发送修改单个教室设备状态消息notes", produces = "application/json")
    @RequestMapping(value="/deviceMonitor/{dmid}", method= RequestMethod.GET)
    public String sendDeviceMonitorSingle(@PathVariable("dmid") int dmid,
                                          @RequestParam(value="device")String device,
                                          @RequestParam(value="operation")String operation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        try{
            System.out.println(dmid+" "+device);
            deviceInfo = deviceInfoService.selectById(dmid);

            NewWebSocket nbs = new NewWebSocket();

            String orderOpen = Constant.OPEN+"_"+device;
            String orderClose = Constant.CLOSE+"_"+device;

            if(device.length()!=0 && !device.equals("camera")){//操作单个设备，不包含摄像头
                nbs.sendDeviceMessageToOne(deviceInfo.getRaspberryCode(),
                        operation.equals("close")?orderClose:orderOpen);//在线时，可以将设备关闭；离线或异常时，可开启
            }else{
                //根据前端返回的信息，判断是开启全部，还是关闭全部
                nbs.sendDeviceMessageToOne(deviceInfo.getRaspberryCode(),
                        operation.equals("open")?Constant.OPENALL:Constant.CLOSEALL);
            }

            dealMessage.addMessageList(deviceInfo.getRaspberryCode(),deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),messageList,deviceInfo,messageListCenter);

            //修改成功
            jsonData.put("judge","0");
        }catch (DataAccessException e){
            //修改失败
            jsonData.put("judge","-9");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id发送修改单个教室摄像头状态消息", notes = "根据id发送修改单个教室摄像头状态消息notes", produces = "application/json")
    @RequestMapping(value="/deviceMonitor/camera/{cid}", method= RequestMethod.GET)
    public String sendCameraMonitorSingle(@PathVariable("cid") int cid,
                                          @RequestParam(value="operation")String operation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        try{
            System.out.println(cid);

            camera = cameraService.selectById(cid);
            deviceInfo = deviceInfoService.selectById(camera.getDid());

            NewWebSocket nbs = new NewWebSocket();

            String orderOpen = Constant.OPEN+"_camera";
            String orderClose = Constant.CLOSE+"_camera";

            nbs.sendDeviceMessageToOne(deviceInfo.getRaspberryCode(),
                operation.equals("close")?orderClose:orderOpen);//在线时，可以将设备关闭；离线或异常时，可开启

            dealMessage.addMessageList(deviceInfo.getRaspberryCode(),deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),messageList,deviceInfo,messageListCenter);

            //修改成功
            jsonData.put("judge","0");
        }catch (DataAccessException e){
            //修改失败
            jsonData.put("judge","-9");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "发消息修改全部教室设备状态", notes = "发消息修改全部教室设备状态notes", produces = "application/json")
    @RequestMapping(value="/deviceMonitor/all", method= RequestMethod.GET)
    public String sendDeviceMonitorAll(@RequestParam(value="operation")String operation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        try{

            NewWebSocket nbs = new NewWebSocket();

            deviceInfoList = deviceInfoService.getAllDeviceInfoStatus();

            for(int i=0;i<deviceInfoList.size();i++){
                nbs.sendDeviceMessageToOne(deviceInfoList.get(i).getRaspberryCode(),
                        operation.equals("open")?Constant.OPENALL:Constant.CLOSEALL);
                //System.out.println("test");
                dealMessage.addMessageList(deviceInfoList.get(i).getRaspberryCode(),deviceInfoList.get(i).getBuildingNum()+deviceInfoList.get(i).getClassroomNum(),messageList,deviceInfoList.get(i),messageListCenter);
            }

            //修改成功
            jsonData.put("judge","0");
        }catch (DataAccessException e){
            //修改失败
            jsonData.put("judge","-9");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


}
