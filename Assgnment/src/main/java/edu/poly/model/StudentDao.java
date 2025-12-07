/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.model;

import edu.poly.db.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dzboy2k
 */
public class StudentDao {
    public boolean insertStudent(Student entity) throws Exception{
        String sql = "insert into students(masv,hoten, diachi, gioitinh, email, sodt, hinh) "
                + " values(?,?,?,?,?,?,?) ";
        try(
                Connection con = DatabaseUtils.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
           pstmt.setString(1, entity.getMaSV());
           pstmt.setString(2, entity.getHoTen());
           pstmt.setString(3, entity.getDiaChi());
           pstmt.setBoolean(4, entity.isGioiTinh());
           pstmt.setString(5, entity.getEmail());
           pstmt.setString(6, entity.getSoDT());
           pstmt.setString(7, entity.getHinh());
           
           return pstmt.executeUpdate() > 0;
        }
    }
    public boolean updateStudent(Student entity) throws Exception{
        String sql = "update students set hoten = ?, diachi =?, gioitinh=?, "
                + " email = ?, sodt = ?, hinh=? where masv = ?";
        try(
                Connection con = DatabaseUtils.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
           pstmt.setString(7, entity.getMaSV());
           pstmt.setString(1, entity.getHoTen());
           pstmt.setString(2, entity.getDiaChi());
           pstmt.setBoolean(3, entity.isGioiTinh());
           pstmt.setString(4, entity.getEmail());
           pstmt.setString(5, entity.getSoDT());
           pstmt.setString(6, entity.getHinh());
           
           return pstmt.executeUpdate() > 0;
        }
    }
    public boolean deleteStudent(String id) throws Exception{
        String sql = "delete from students where masv = ?";
        try(
                Connection con = DatabaseUtils.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
           pstmt.setString(1, id);
           
           return pstmt.executeUpdate() > 0;
        }
    }
    public Student findStudentById(String id) throws Exception{
        String sql = "select * from students where masv = ?";
        try(
                Connection con = DatabaseUtils.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
           pstmt.setString(1, id);
           
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                Student student = new Student();
                student.setMaSV(rs.getString("maSV"));
                student.setHoTen(rs.getString("hoten"));
                student.setDiaChi(rs.getString("diachi"));
                student.setEmail(rs.getString("email"));
                student.setSoDT(rs.getString("sodt"));
                student.setHinh(rs.getString("hinh"));
                
                return student;
            }
            return null;
        }
    }
    public List<Student> findStudents() throws Exception{
        String sql = "select * from students";
        try(
                Connection con = DatabaseUtils.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            
            ResultSet rs = pstmt.executeQuery();
            List<Student> list = new ArrayList<>();
            while (rs.next()){
                Student student = new Student();
                student.setMaSV(rs.getString("maSV"));
                student.setHoTen(rs.getString("hoten"));
                student.setDiaChi(rs.getString("diachi"));
                student.setEmail(rs.getString("email"));
                student.setSoDT(rs.getString("sodt"));
                student.setHinh(rs.getString("hinh"));
                
                list.add(student);
            }
            return list;
        }
    }
}
