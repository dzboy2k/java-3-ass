/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.poly.app.dao;

import edu.poly.poly.app.helpers.DatabaseHelper;
import edu.poly.poly.app.model.BangDiem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dzboy2k
 */
public class BangDiemDao {
    public boolean insert(BangDiem bd) throws Exception{
        
        String sql = "INSERT INTO [dbo].[BangDiem] ([MaSinhVien],[TiengAnh],[TinHoc],[GDTC]) VALUES(?,?,?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            pstmt.setString(1, bd.getMaSinhVien());
            pstmt.setFloat(2, bd.getTiengAnh());
            pstmt.setFloat(3, bd.getTinHoc());
            pstmt.setFloat(4, bd.getGDTC());
            
            return pstmt.executeUpdate()>0;
        }
    }
    public boolean update(BangDiem bd) throws Exception{
        
        String sql = "UPDATE [dbo].[BangDiem] " +
                    " SET [TiengAnh] = ?,[TinHoc] = ?,[GDTC] = ?" +
                    " WHERE [MaSinhVien] = ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            pstmt.setString(4, bd.getMaSinhVien());
            pstmt.setFloat(1, bd.getTiengAnh());
            pstmt.setFloat(2, bd.getTinHoc());
            pstmt.setFloat(3, bd.getGDTC());
            
            return pstmt.executeUpdate()>0;
        }
    }
    public boolean deleteByMaSinhVien(String maSinhVien) throws Exception{
        
        String sql = "delete from bangdiem " +
                    " WHERE [MaSinhVien] = ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            pstmt.setString(1, maSinhVien);
            
            return pstmt.executeUpdate()>0;
        }
    }
    public BangDiem findByMaSinhVien(String maSinhVien) throws Exception{ 
        String sql = "select * from bangdiem WHERE [MaSinhVien] = ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            pstmt.setString(1, maSinhVien);
            
            try(ResultSet rs = pstmt.executeQuery();){
                if (rs.next()){
                    BangDiem bd = createBangDiem(rs);
                    
                    return bd;
                }
            }
            return null;
        }
    }
    public List<BangDiem> findAll() throws Exception{ 
        String sql = "select * from bangdiem ";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            
            try(ResultSet rs = pstmt.executeQuery();){
                List<BangDiem> list = new ArrayList<>();
                while (rs.next()){
                    BangDiem bd = createBangDiem(rs);
                    list.add(bd);
                }
                return list;
            }
        }
    }
    public List<BangDiem> findTop(int top) throws Exception{ 
        String sql = "select top "+ top +" *, (TiengAnh + TinHoc + GDTC)/3 as DTB " + 
                " from bangdiem order by dtb desc ";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            
            try(ResultSet rs = pstmt.executeQuery();){
                List<BangDiem> list = new ArrayList<>();
                while (rs.next()){
                    BangDiem bd = createBangDiem(rs);
                    list.add(bd);
                }
                return list;
            }
        }
    }

    private BangDiem createBangDiem(final ResultSet rs) throws SQLException {
        BangDiem bd = new BangDiem();
        bd.setMa(rs.getInt("Ma"));
        bd.setMaSinhVien(rs.getString("maSinhVien"));
        bd.setTiengAnh(rs.getFloat("TiengAnh"));
        bd.setTinHoc(rs.getFloat("TinHoc"));
        bd.setGDTC(rs.getFloat("GDTC"));
        return bd;
    }
}
