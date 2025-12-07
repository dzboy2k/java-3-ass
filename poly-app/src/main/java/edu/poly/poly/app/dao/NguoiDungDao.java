/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.poly.app.dao;

import edu.poly.poly.app.helpers.DatabaseHelper;
import edu.poly.poly.app.model.NguoiDung;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author dzboy2k
 */
public class NguoiDungDao {
    public NguoiDung checkLogin(String tenDangNhap, String matKhau)
        throws Exception{
        String sql = "select tenDangNhap, matKhau, vaitro from nguoidung "
                + " where tenDangNhap=? and matKhau = ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
           ){
            pstmt.setString(1, tenDangNhap);
            pstmt.setString(2, matKhau);
            
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()){
                    NguoiDung nd = new NguoiDung();
                    nd.setTenDangNhap(tenDangNhap);
                    nd.setVaiTro(rs.getString("vaitro"));
                    return nd;
                }
            }
        }
        return null;
    }
}
