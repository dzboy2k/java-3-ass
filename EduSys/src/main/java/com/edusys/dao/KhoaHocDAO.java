/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.KhoaHoc;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dzboy2k
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer>{
    
    final String INSERT_SQL = "INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
    final String UPDATE_SQL = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH=?";
    final String DELETE_SQL = "DELETE FROM KhoaHoc WHERE MaKH=?";
    final String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
    final String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc where MaKH=?";
    final String SELECT_BY_MA_CD_SQL = "SELECT * FROM KhoaHoc where MaCD=?";
    
    @Override
    public void insert(KhoaHoc model) {
        JdbcHelper.update(INSERT_SQL, 
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKG(),
                model.getGhiChu(),
                model.getMaNV());
    }

    @Override
    public void update(KhoaHoc model) {
        JdbcHelper.update(UPDATE_SQL, 
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKG(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {                
                KhoaHoc model = new KhoaHoc();
                model.setMaKH(rs.getInt("MaKH"));
                model.setHocPhi(rs.getDouble("HocPhi"));
                model.setThoiLuong(rs.getInt("ThoiLuong"));
                model.setNgayKG(rs.getDate("NgayKG"));
                model.setGhiChu(rs.getString("GhiChu"));
                model.setMaNV(rs.getString("MaNV"));
                model.setNgayTao(rs.getDate("NgayTao"));
                model.setMaCD(rs.getString("MaCD"));
                list.add(model);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<KhoaHoc> selectKhoaHocByChuyenDe(String maCD) {
        return selectBySql(SELECT_BY_MA_CD_SQL, maCD);
    }
    
    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT year(NgayKG) Year FROM KhoaHoc ORDER BY Year DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {                
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
