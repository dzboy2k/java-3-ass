/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.HocVien;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dzboy2k
 */
public class HocVienDAO extends EduSysDAO<HocVien, Integer> {
    
    final String INSERT_SQL = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
    final String UPDATE_SQL = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
    final String DELETE_SQL = "DELETE FROM HocVien WHERE MaHV=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HocVien";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HocVien where MaHV=?";
    final String SELECT_BY_KHOA_HOC_SQL = "SELECT * FROM HocVien where MaKH=?";

    @Override
    public void insert(HocVien model) {
        JdbcHelper.update(INSERT_SQL, 
                model.getMaKH(),
                model.getMaNH(),
                model.getDiem());
    }

    @Override
    public void update(HocVien model) {
        JdbcHelper.update(UPDATE_SQL, 
                model.getMaKH(),
                model.getMaNH(),
                model.getDiem(),
                model.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HocVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {                
                HocVien model = new HocVien();
                model.setMaHV(rs.getInt("MaHV"));
                model.setMaKH(rs.getInt("MaKH"));
                model.setMaNH(rs.getString("MaNH"));
                model.setDiem(rs.getDouble("Diem"));
                list.add(model);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<HocVien> selectByKhoaHoc(int maKH) {
        return selectBySql(SELECT_BY_KHOA_HOC_SQL, maKH);
    }
    
}
