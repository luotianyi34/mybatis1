package com.xuhai.test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author PangJunjie
 * @Date 2022/7/29/029
 */
public abstract class BaseDao {

    /*声明连接器*/
    private Connection connection;
    /*声明执行器*/
    private PreparedStatement statement;
    /*声明结果集*/
    private ResultSet resultSet;

    public BaseDao(){
        /*加载配置文件*/
//        ResourceBundle rb = ResourceBundle.getBundle("db");

        /*初始化驱动程序*/
        final String DRIVER="com.mysql.cj.jdbc.Driver";
        /*初始化链接地址*/
        final String URL = "jdbc:mysql://localhost:3306/car?useSSL=false&serverTimezone=Asia/Shanghai&charsetEncoding=utf-8";
        /*初始化用户名*/
        final String USERNAME = "root";
        /*初始化密码*/
        final String PASSWORD = "1234";
        try {
            Class.forName(DRIVER);
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("加载驱动或链接数据库异常");
            throw new RuntimeException(e);
        }
    }

    /**
     * 通用增删改
     * @param sql   要执行的SQL语句
     * @param objs  SQL中占位符组成的数组
     * @return      受影响行数
     */
    protected int baseUpdate(String sql,Object[] objs){
        int result =0;
        try {
            formatterStatement(sql, objs);
            result=statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 通用增删改--无占位符时使用
     * @param sql 要执行的SQL
     * @return 受影响的行数
     */
    protected int baseUpdate(String sql){
        return baseUpdate(sql,null);
    }

    /**
     * 通用查询
     * @param sql 要执行的SQL语句
     * @param objs 占位符数组
     * @param tClass 泛型类
     * @return  封装后的List集合
     * @param <T>   定义泛型
     */
    protected <T> List<T> baseQuery(String sql,Object[] objs,Class<T> tClass){
        /*初始化结果集*/
        List<T> tList = new ArrayList<>();
        try {
            /*处理SQL语句和占位符*/
            formatterStatement(sql, objs);
            /*执行SQL并获取结果集*/
            resultSet=statement.executeQuery();
            /*获取数据库表的元数据(包含列名，列的个数等信息)*/
            ResultSetMetaData metaData = resultSet.getMetaData();
            /*遍历ResultSet结果集*/
            while (resultSet.next()){
                /*通过Class类实例化泛型*/
                T t = tClass.newInstance();
                /*通过反射获取所有的属性*/
                Field[] fs = tClass.getDeclaredFields();
                /*遍历所有列名*/
                for(int i=0;i< metaData.getColumnCount();i++){
                    /*获取列名*/
                    String columnName = metaData.getColumnName(i+1);
                    /*格式化操作列名*/
                    StringBuilder newColumnName=new StringBuilder(columnName);
                    /*判断列名中是否存在下划线(_)*/
                    if(columnName.contains("_")){
                        /*根据下划线(_)拆分字符串为数组*/
                        String[] cns = columnName.split("_");
                        /*重新给格式化的列名赋值*/
                        newColumnName=new StringBuilder(cns[0]);
                        /*遍历剩下的数组内容*/
                        for(int j=1;j<cns.length;j++){
                            /*获取剩余部分的单词*/
                            String cn = cns[j];
                            /*获取单词的首字母并大写*/
                            String w = cn.substring(0,1).toUpperCase();
                            /*获取单词除首字母外剩余部分*/
                            String d = cn.substring(1);
                            /*拼成新的单词*/
                            String word = w + d;
                            /*追加到格式化的列名中*/
                            newColumnName.append(word);
                        }
                    }
                    /*遍历所有属性*/
                    for(Field f :fs){
                        /*获取属性名*/
                        String fieldName = f.getName();
                        /*判断属性名和列名是否相等*/
                        if(fieldName.equals(newColumnName.toString())){
                            /*开启赋值功能*/
                            f.setAccessible(true);
                            /*赋值*/
                            f.set(t,resultSet.getObject(columnName));
                        }
                    }
                }
                /*封装后的实体类存入list集合*/
                tList.add(t);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        /*返回结果集*/
        return tList;
    }

    /**
     * 通用查询--无参数
     */
    protected <T> List<T> baseQuery(String sql,Class<T> tClass){
        return baseQuery(sql,new Object[]{},tClass);
    }

    protected <T> List<T> baseQuery(String sql,List<Object> objs,Class<T> tClass){
        Object[] os = objs.toArray(new Object[objs.size()]);
        return baseQuery(sql,os,tClass);
    }
    /**
     * 通用查询--查单条
     * @param sql       要执行的SQL
     * @param objs      占位符数组
     * @param tClass    类对象
     * @return          结果实例
     */
    protected <T> T baseQueryBean(String sql,Object[] objs,Class<T> tClass){
        List<T> tList = baseQuery(sql,objs,tClass);
        if(tList.size()==1){
            return tList.get(0);
        }
        return null;
    }

    /**
     * 查询总条数
     * @param sql
     * @param objs
     * @return
     */
    protected int getCount(String sql,Object[] objs){
        formatterStatement(sql, objs);
        try {
            resultSet= statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    protected int getCount(String sql,List<Object> objs){
        Object[] os = objs.toArray(new Object[objs.size()]);
        return getCount(sql,os);
    }

    /**
     * 关闭连接
     */
    protected void closeAll(){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 处理SQL和占位符
     * @param sql
     * @param objs
     */
    private void formatterStatement(String sql,Object[] objs){
        try {
            statement= connection.prepareStatement(sql);
            if(objs!=null&&objs.length>0){
                for(int i=0;i<objs.length;i++){
                    statement.setObject(i+1,objs[i]);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
