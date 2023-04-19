package com.xuhai.test;

import com.xuhai.entity.Department;
import com.xuhai.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author PangJunjie
 * @Date 2023/4/18/018
 */
public class Test07 {
    public static void main(String[] args) throws IOException {
        /*加载mybatis的配置文件*/
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        /*创建mybatis的执行工厂*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        /*获取执行SQL的session*/
        SqlSession session = factory.openSession();
        DepartmentMapper departmentMapper = session.getMapper(DepartmentMapper.class);

        String name = "部门2";
        String profile = "介绍2";
        int status = 2;

        int id = 13;
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setProfile(profile);
        department.setStatus(status);

//        int result = departmentMapper.addInfo(name,profile,status);
//        /*mybatis默认启用事务管理，增删改都需要提交事务*/
//        session.commit();
//        System.out.println(result>0?"添加成功":"添加失败");

//        int result = departmentMapper.updateInfo(department);
//        if(result>0){
//            session.commit();
//            System.out.println("修改成功");
//        }else{
//            System.out.println("修改失败");
//        }

//        int result = departmentMapper.deleteInfo(id);
//        if(result>0){
//            session.commit();
//            System.out.println("删除成功");
//        }else{
//            System.out.println("删除失败");
//        }

        int result = departmentMapper.add(department);
        if(result>0){
            session.commit();
            System.out.println("添加成功");
            System.out.println("最新ID："+department.getId());
        }else{
            System.out.println("添加失败");
        }
        int result2 = departmentMapper.add2(department);

        if(result2>0){
            session.commit();
            System.out.println("添加成功2");
            System.out.println("最新ID："+department.getId());
        }else{
            System.out.println("添加失败2");
        }
        System.out.println(departmentMapper.getAll());
    }
}
