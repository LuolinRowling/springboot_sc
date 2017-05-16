package com.pku.system.controller;

import com.pku.system.model.*;
import com.pku.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Api(value="分配设备",tags = {"分配设备API"},description = "描述信息")
@RestController
@RequestMapping(value = "/assignDevice")
public class AssignDeviceController {
    @Autowired
    CameraTypeService cameraTypeService;
    @Autowired
    ComputerTypeService computerTypeService;
    @Autowired
    ProjectorTypeService projectorTypeService;
    @Autowired
    RaspberryTypeService raspberryTypeService;
    @Autowired
    SinglechipTypeService singlechipTypeService;

    @Autowired
    CameraService cameraService;

    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    ClassroomService classroomService;

    /**
     * 分配设备
     * @return
     */
    @ApiOperation(value = "获得教室下所有已分配设备列表", notes = "获得教室下所有已分配设备列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllAssignDevice(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        List<DeviceInfo> deviceInfoList= deviceInfoService.getAllDeviceInfo();

        for(int i = 0;i < deviceInfoList.size();i++){
            System.out.println(deviceInfoList.get(i).getId());
            List<Camera> cameraList = cameraService.selectByDeviceId(deviceInfoList.get(i).getId());
            deviceInfoList.get(i).setCameraList(cameraList);

            jsonArray.add(deviceInfoList.get(i));
            jsonData.put("deviceInfoList",jsonArray);
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 添加教室所对应的设备,获取数据库中的设备型号列表，教学楼教室列表
     * @return
     */
    @ApiOperation(value = "获取数据库中的设备型号列表，教学楼教室列表", notes = "获取数据库中的设备型号列表，教学楼教室列表notes", produces = "application/json")
    @RequestMapping(value="/deviceList", method= RequestMethod.GET)
    public String addAssignDevice(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        //设备型号
        List<CameraType> cameraTypeList= cameraTypeService.getAllCameraType();
        List<ComputerType> computerTypeList= computerTypeService.getAllComputerType();
        List<ProjectorType> projectorTypeList = projectorTypeService.getAllProjectorType();
        List<RaspberryType> raspberryTypeList = raspberryTypeService.getAllRaspberryType();
        List<SinglechipType> singlechipTypeList = singlechipTypeService.getAllSinglechipType();

        //教学楼教室，默认加载第一个教学楼下的所有教室
        List<Building> buildingList = buildingService.getAllBuilding();
        List<Classroom> classroomList = classroomService.selectByBuildingId(1);

        jsonData.put("cameraTypeList",cameraTypeList);
        jsonData.put("computerTypeList",computerTypeList);
        jsonData.put("projectorTypeList",projectorTypeList);
        jsonData.put("raspberryTypeList",raspberryTypeList);
        jsonData.put("singleChipTypeList",singlechipTypeList);
        jsonData.put("buildingList",buildingList);
        jsonData.put("classroomList",classroomList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }

    /**
     * 添加教室中的设备
     * @param bid
     * @return
     */
    @RequestMapping(value="/ajax_change_building", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String ajaxAddClassroom(@RequestParam(value="bid")int bid){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<Classroom> classroomList = classroomService.selectByBuildingId(bid);
        jsonData.put("classroomlist",classroomList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 添加教室中的设备
     * @param buildingNum
     * @param classroomNum
     * @param singlechipTypeId
     * @param raspberryTypeId
     * @param cameraTypeId
     * @param computerTypeId
     * @param projectorTypeId
     * @return
     */
    @RequestMapping(value="/ajax_add_Classroom", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String ajaxAddClassroom(@RequestParam(value="buildingNum")String buildingNum,
                            @RequestParam(value="classroomNum")String classroomNum,
                            @RequestParam(value="singlechipTypeId")int singlechipTypeId,
                            @RequestParam(value="raspberryTypeId")int raspberryTypeId,
                            @RequestParam(value="cameraTypeId")int cameraTypeId,
                            @RequestParam(value="computerTypeId")int computerTypeId,
                            @RequestParam(value="projectorTypeId")int projectorTypeId,
                            @RequestParam(value="raspberryCode")String raspberryCode){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(buildingNum.length()==0){
            //判断教学楼号是否为空
            jsonData.put("judge","-1");
        }else if(classroomNum.length()==0){
            //判断教室号是否为空
            jsonData.put("judge","-2");
        }else if(deviceInfoService.selectByBuildingClassroom(buildingNum,classroomNum)!=null){
            //判断同一个教学楼的教室是否已经分配设备
            jsonData.put("judge","-3");
        }else{
            try{
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setBuildingNum(buildingNum);
                deviceInfo.setClassroomNum(classroomNum);
                //deviceInfo.setCameraTypeId(cameraTypeId);
                deviceInfo.setComputerTypeId(computerTypeId);
                deviceInfo.setProjectorTypeId(projectorTypeId);
                deviceInfo.setRaspberryTypeId(raspberryTypeId);
                deviceInfo.setSinglechipTypeId(singlechipTypeId);
                deviceInfo.setRaspberryCode(raspberryCode);
                deviceInfoService.addDeviceInfo(deviceInfo);
                //添加成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 更新教室所对应的设备
     * @return
     */
    @RequestMapping(value="/editClassroom")
    public ModelAndView editAssignDevice(){
        ModelAndView mv= new ModelAndView();
        List<CameraType> cameraTypeList= cameraTypeService.getAllCameraType();
        List<ComputerType> computerTypeList= computerTypeService.getAllComputerType();
        List<ProjectorType> projectorTypeList = projectorTypeService.getAllProjectorType();
        List<RaspberryType> raspberryTypeList = raspberryTypeService.getAllRaspberryType();
        List<SinglechipType> singlechipTypeList = singlechipTypeService.getAllSinglechipType();

        mv.addObject("cameraTypelist",cameraTypeList);
        mv.addObject("computerTypelist",computerTypeList);
        mv.addObject("projectorTypelist",projectorTypeList);
        mv.addObject("raspberryTypelist",raspberryTypeList);
        mv.addObject("singlechipTypelist",singlechipTypeList);
        mv.setViewName("editClassroom");
        return mv;
    }

    /**
     * 根据did获取为教室分配设备信息
     * @param did
     */
    @RequestMapping(value="/ajax_get_classroom_info", produces = "text/html;charset=UTF-8")
    public @ResponseBody String ajaxGetAddClassroomInfo(@RequestParam(value="did")int did){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        DeviceInfo deviceInfo = deviceInfoService.selectById(did);
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
     * 更新教室中的设备
     * @param did
     * @param singlechipTypeId
     * @param raspberryTypeId
     * @param cameraTypeId
     * @param computerTypeId
     * @param projectorTypeId
     * @return
     */
    @RequestMapping(value="/ajax_edit_classroom", produces = "text/html;charset=UTF-8")
    public @ResponseBody String ajaxEditAddClassroom(@RequestParam(value="did")int did,
                                                     @RequestParam(value="singlechipTypeId")int singlechipTypeId,
                                                     @RequestParam(value="raspberryTypeId")int raspberryTypeId,
                                                     @RequestParam(value="cameraTypeId")int cameraTypeId,
                                                     @RequestParam(value="computerTypeId")int computerTypeId,
                                                     @RequestParam(value="projectorTypeId")int projectorTypeId,
                                                     @RequestParam(value="raspberryCode")String raspberryCode){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        try{
            System.out.println(did+" "+singlechipTypeId+" "+raspberryTypeId+" "+cameraTypeId+" "+computerTypeId+" "+projectorTypeId);
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setId(did);
            deviceInfo.setSinglechipTypeId(singlechipTypeId);
            deviceInfo.setRaspberryTypeId(raspberryTypeId);
            deviceInfo.setSinglechipTypeId(singlechipTypeId);
            deviceInfo.setProjectorTypeId(projectorTypeId);
            //deviceInfo.setCameraTypeId(cameraTypeId);
            deviceInfo.setComputerTypeId(computerTypeId);
            deviceInfo.setRaspberryCode(raspberryCode);
            deviceInfoService.updateDeviceInfo(deviceInfo);
            //修改成功
            jsonData.put("judge","0");
        }catch (DataAccessException e){
            //修改失败
            jsonData.put("judge","-9");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 删除教室中的设备
     * @param did
     */
    @RequestMapping(value="/ajax_delete_classroom", produces = "text/html;charset=UTF-8")
    public @ResponseBody String ajaxDeleteClassroom(@RequestParam(value="did")int did){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(deviceInfoService.selectById(did)==null){
            //判断用户名是否存在
            jsonData.put("judge","-1");
        }else{
            try{

                deviceInfoService.deleteDeviceInfo(did);
                //删除成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //删除失败
                jsonData.put("judge","-9");
            }
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


}
