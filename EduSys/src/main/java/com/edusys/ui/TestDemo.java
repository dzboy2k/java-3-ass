/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.ThongKeDao;
import java.util.List;

/**
 *
 * @author dzboy2k
 */
public class TestDemo {
    public static void main(String[] args) {
        ThongKeDao tkDao = new ThongKeDao();
        List<Object[]> list = tkDao.getDiemChuyenDe();
        for (Object[] obj : list) {
            System.out.println("=>"+obj[0]+"-"+obj[1]+"-"+obj[2]);
        }
//        NhanVienDao dao = new NhanVienDao();
//        dao.insert(new NhanVien("Namnv55", "Nguyen Van Nam", "admin123", true));
//        List<NhanVien> ls = dao.selectAll();
//        for (NhanVien nv : ls) {
//            System.out.println("=>"+nv.toString());
//        }
    }
}
