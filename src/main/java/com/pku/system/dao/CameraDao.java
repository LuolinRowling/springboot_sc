package com.pku.system.dao;

import com.pku.system.model.Camera;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangdongyu on 2017/4/24.
 */
@Mapper
public interface CameraDao {
    @Select("select * from camera where id = #{id}")
    public Camera selectById(int id);

    @Select("select * from camera where did = #{did}")
    public List<Camera> selectByDeviceId(int did);

    @Insert("insert into camera (id,cameraTypeId,cameraStatus,cameraAngle,did) values (#{id},#{cameraTypeId},#{cameraStatus},#{cameraAngle},#{did})")
    public void addCamera(Camera camera);

    @Update("update camera set cameraTypeId=#{cameraTypeId},cameraStatus=#{cameraStatus},cameraAngle=#{cameraAngle} where id=#{id}")
    public void updateCamera(Camera camera);

    @Delete("delete from camera where id=#{id}")
    public void deleteCamera(int id);
}
