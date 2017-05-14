package com.pku.system.dao;

import com.pku.system.model.Camera;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangdongyu on 2017/4/24.
 */
@Mapper
public interface CameraDao {
    @Select("select * from camera where cameraId = #{cameraId}")
    public Camera selectById(int cameraId);

    @Select("select * from camera where did = #{did}")
    public List<Camera> selectByDeviceId(int did);

    @Insert("insert into camera (cameraId,cameraTypeId,cameraStatus,cameraAngle,did) values (#cameraId},#{cameraTypeId},#{cameraStatus},#{cameraAngle},#{did})")
    public void addCamera(Camera camera);

    @Update("update camera set cameraTypeId=#{cameraTypeId},cameraStatus=#{cameraStatus},cameraAngle=#{cameraAngle} where cameraId=#{cameraId}")
    public void updateCamera(Camera camera);

    @Delete("delete from camera where cameraId=#{cameraId}")
    public void deleteCamera(int cameraId);
}
