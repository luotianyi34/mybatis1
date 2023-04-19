package com.xuhai.test;

import com.xuhai.entity.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author PangJunjie
 * @Date 2023/4/18/018
 */
public class Test02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*1.加载驱动*/
        Class.forName("com.mysql.cj.jdbc.Driver");
        /*2.打开数据库连接*/
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car?useSSL=false&serverTimezone=Asia/Shanghai&charsetEncoding=utf-8","root","1234");
        /*3.编写SQL*/
        String sql = "select * from brand";
        /*4.获取SQL执行器*/
        PreparedStatement pstm = connection.prepareStatement(sql);
        /*5.执行SQL并获取结果集*/
        ResultSet rs = pstm.executeQuery();
        /*6.处理结果集*/
        List<Brand> brandList = new ArrayList<>();
        while (rs.next()){
            Brand brand = new Brand();
            Integer id = rs.getInt("id");
            brand.setId(id);

            brand.setBrandName(rs.getString("brand_name"));
            brand.setProfile(rs.getString("profile"));
            brand.setStatus(rs.getInt("status"));

            brandList.add(brand);
        }
        /*7.输出结果*/
        for(Brand brand : brandList){
            System.out.println(brand);
        }

    }
}
