package com.xuhai.test;

import com.xuhai.entity.Brand;

import java.util.List;

/**
 * @Author PangJunjie
 * @Date 2023/4/18/018
 */
public class Test03 extends BaseDao {
    private void getAll(){
        String sql = "select * from brand";
        List<Brand> brandList = baseQuery(sql,Brand.class);
        for(Brand brand : brandList){
            System.out.println(brand);
        }
    }
    public static void main(String[] args) {
        Test03 test03 = new Test03();
        test03.getAll();
    }
}
