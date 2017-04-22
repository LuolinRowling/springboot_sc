package com.pku.system.dao;

import com.pku.system.model.Classroom;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiangdongyu on 2017/4/22.
 */
@Mapper
public interface ClassroomDao {
    @Select("select * from classroom where id = #{id}")
    public Classroom selectById(int id);

    @Select("select * from classroom where classroomNum = #{classroomNum}")
    public Classroom selectByName(String classroomNum);

    @Select("select * from classroom where b_id = #{b_id}")
    public List<Classroom> selectByBuildingId(int bid);

    @Select("SELECT" +
            "c.id," +
            "c.classroomNum," +
            "b.id," +
            "b.buildingNum" +
            "FROM classroom c INNER JOIN building b ON c.b_id=b.id")
    public List<Classroom> getAllClassroom();

    @Insert("insert into classroom (id,classroomNum,b_id) values (#{id},#{classroomNum},#{b_id})")
    public void addClassroom(Classroom classroom);

    @Update("update classroom set classroomNum=#{classroomNum},b_id=#{b_id} where id=#{id}")
    public void updateClassroom(Classroom classroom);

    @Delete("delete from classroom where id=#{id}")
    public void deleteClassroom(int id);

}
