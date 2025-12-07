/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NguoiHoc;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dzboy2k
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String>{
    
    final String INSERT_SQL = "INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? WHERE MaNH=?";
    final String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNH=?";
    final String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc where MaNH=?";
    final String SELECT_NOT_IN_COURSE_SQL = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ? AND MaNH NOT IN(SELECT MaNH FROM HocVien WHERE MaKH=?)";

    @Override
    public void insert(NguoiHoc model) {
        JdbcHelper.update(INSERT_SQL,
                model.getMaNH(),
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV());
    }

    @Override
    public void update(NguoiHoc model) {
        JdbcHelper.update(UPDATE_SQL, 
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaNH());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {                
                NguoiHoc model = new NguoiHoc();
                model.setMaNH(rs.getString("MaNH"));
                model.setHoTen(rs.getString("HoTen"));
                model.setNgaySinh(rs.getDate("NgaySinh"));
                model.setGioiTinh(rs.getBoolean("GioiTinh"));
                model.setDienThoai(rs.getString("DienThoai"));
                model.setEmail(rs.getString("Email"));
                model.setGhiChu(rs.getString("GhiChu"));
                model.setMaNV(rs.getString("MaNV"));
                model.setNgayDK(rs.getDate("NgayDK"));
                list.add(model);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<NguoiHoc> selectNotInCourse(int maKH, String keyword) {
        return selectBySql(SELECT_NOT_IN_COURSE_SQL,"%"+keyword+"%",maKH);
    }
    
}
