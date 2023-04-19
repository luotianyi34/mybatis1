package com.xuhai.test;

import com.xuhai.entity.Brand;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * @Author PangJunjie
 * @Date 2023/4/18/018
 */
public class Test04 {
    public static void main(String[] args) throws IOException {
        System.out.println("请输入要查看的品牌编号：");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();

        /*加载mybatis的配置文件*/
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        /*创建mybatis的执行工厂*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        /*获取执行SQL的session*/
        SqlSession session = factory.openSession();
        /*mybatis在调用相关方法时，默认只允许传递一个参数*/
        Brand brand = session.selectOne("nsBrand.getById",id);
        System.out.println(brand);

        System.out.println("请输入品牌名");
        String brandName = scanner.next();
        Brand bd = new Brand();
        bd.setBrandName(brandName);
        List<Brand> brandList = session.selectList("nsBrand.getAll3",bd);
        System.out.println(brandList);
    }
}
