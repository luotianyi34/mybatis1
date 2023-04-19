package com.xuhai.mapper;

import com.xuhai.entity.Department;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author PangJunjie
 * @Date 2023/4/18/018
 */
public interface DepartmentMapper {

    /*
    1. Mapper接口名称和对应的Mapper.xml文件必须相同
    2. Mapper.xml文件中的namespace与Mapper接口的类路径相同。
    3. Mapper接口方法名和Mapper.xml中定义的每个statement的id相同
    4. Mapper接口方法的输入参数类型和Mapper.xml中定义的每个 sql 的parameterType的类型相同
    5. Mapper接口方法的输出参数类型和Mapper.xml中定义的每个 sql 的resultType的类型相同
     */
    List<Department> getAll();

    Department getById(Integer id);

    @Select("select * from department")
    List<Department> getAll2();

    int addInfo(@Param("name") String name,@Param("profile") String profile,@Param("status") Integer status);

    int updateInfo(Department department);

    int deleteInfo(Integer id);

    int add(Department department);
    int add2(Department department);
}
