package com.xuhai.test;

import com.xuhai.entity.Department;
import com.xuhai.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author PangJunjie
 * @Date 2023/4/18/018
 */
public class Test06 {
    public static void main(String[] args) throws IOException {
        /*加载mybatis的配置文件*/
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        /*创建mybatis的执行工厂*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        /*获取执行SQL的session*/
        SqlSession session = factory.openSession();

        DepartmentMapper departmentMapper = session.getMapper(DepartmentMapper.class);
        List<Department> dList = departmentMapper.getAll();
        for(Department department :dList){
            System.out.println(department);
        }
        System.out.println("==================");
        System.out.println(departmentMapper.getAll2());
    }
}
