package com.xuhai.test;

import com.xuhai.entity.Userinfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test05 {
    public static void main(String[] args) throws IOException {
        /*加载mybatis的配置文件*/
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        /*创建mybatis的执行工厂*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        /*获取执行SQL的session*/
        SqlSession session = factory.openSession();
        List<Userinfo> userList = session.selectList("nsUserinfo.getAll");
        for(Userinfo userinfo:userList){
            System.out.println(userinfo);
        }
    }
}
