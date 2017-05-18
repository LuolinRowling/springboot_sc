package com.pku.system.controller;

import com.pku.system.model.*;
import com.pku.system.service.BuildingService;
import com.pku.system.service.ClassroomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="教学楼教室信息管理",tags = {"教学楼教室信息管理API"},description = "描述信息")
@RestController
@RequestMapping("/buildingClassrooms")
public class BuildingClassroomController {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ClassroomService classroomService;


    @ApiOperation(value = "获得教学楼教室信息列表", notes = "获得教学楼教室信息列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllBuildingClassroom(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        List<Classroom> classroomList = classroomService.getAllClassroom();
        //List<Building> buildingList= buildingService.getAllBuilding();

//        for(int i = 0;i < buildingList.size();i++){
//            System.out.println(buildingList.get(i).getId());
//            List<Classroom> classroomListB = classroomService.selectByBuildingId(buildingList.get(i).getId());
//            buildingList.get(i).setClassroomList(classroomListB);
//        }

        for(int i = 0;i < classroomList.size();i++){
            System.out.println(classroomList.get(i).getB_id());
            Building building = buildingService.selectById(classroomList.get(i).getB_id());
            classroomList.get(i).setBuilding(building);

            jsonArray.add(classroomList.get(i));
            jsonData.put("buildingClassroomList",jsonArray);
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "添加教学楼教室信息", notes = "添加教学楼教室信息notes", produces = "application/json")
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public String postBuildingClassroom(@RequestBody Building building) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(building.getBuildingNum().length()==0){
            //判断教学楼是否为空
            jsonData.put("judge","-1");
        }else if(building.getClassroomList().size()==0){
            //判断教室是否为空
            jsonData.put("judge","-2");
        }
        if(buildingService.selectByName(building.getBuildingNum()) == null){
            //判断教学楼是否存在，不存在则新增
            try{
                buildingService.addBuilding(building);
                //添加成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }
        }
        if(buildingService.selectByName(building.getBuildingNum()) != null){
            //判断教学楼是否存在，存在则新增教室
            for(int i=0;i<building.getClassroomList().size();i++){
                if(classroomService.selectByName(building.getClassroomList().get(i).getClassroomNum())!=null){
                    jsonData.put("judge","-4");
                }else{
                    try{
                        Building buildingC = buildingService.selectByName(building.getBuildingNum());

                        Classroom classroom = new Classroom();
                        classroom.setClassroomNum(building.getClassroomList().get(i).getClassroomNum());
                        classroom.setB_id(buildingC.getId());

                        classroomService.addClassroom(classroom);
                        //添加成功
                        jsonData.put("judge","0");
                    }catch (DataAccessException e){
                        //添加失败
                        jsonData.put("judge","-9");
                    }
                }
            }

        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id查询教学楼教室信息", notes = "根据id查询教学楼教室信息notes", produces = "application/json")
    @RequestMapping(value="/{cid}", method=RequestMethod.GET)
    public String getUser(@PathVariable("cid") int cid) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Classroom classroom = classroomService.selectById(cid);
        Building building = buildingService.selectById(classroom.getB_id());

        if(classroom == null||building == null){
            jsonData.put("judge","-9");
        }else{
            jsonData.put("building",building);
            jsonData.put("classroom",classroom);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改教学楼教室信息", notes = "根据id修改教学楼教室信息notes", produces = "application/json")
    @RequestMapping(value="/{cid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putUser(@PathVariable("cid") int cid,
                          @RequestBody Classroom classroom) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(classroom.getBuilding().getBuildingNum().length()==0){
            //判断教学楼是否为空
            jsonData.put("judge","-1");
        }else if(classroom.getClassroomNum().length()==0){
            //判断教室是否为空
            jsonData.put("judge","-2");
        }else if(buildingService.selectByName(classroom.getBuilding().getBuildingNum())!=null&&classroomService.selectByName(classroom.getClassroomNum())!=null){
            //教学楼教室已存在
            jsonData.put("judge","-3");
        }else{
            try{
                Classroom classroomOld = classroomService.selectById(cid);

                classroomOld.setClassroomNum(classroom.getClassroomNum());

                Building building = buildingService.selectById(classroom.getB_id());
                building.setBuildingNum(classroom.getBuilding().getBuildingNum());

                buildingService.updateBuilding(building);
                classroomService.updateClassroom(classroom);

                //修改成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //修改失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id删除教室信息", notes = "根据id删除教室信息notes", produces = "application/json")
    @RequestMapping(value="/{cid}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable("cid") int cid) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(classroomService.selectById(cid)==null){
            jsonData.put("judge","-1");
        }else{
            try{

                classroomService.deleteClassroom(cid);
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
