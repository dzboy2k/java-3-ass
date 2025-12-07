/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.model;

import edu.poly.db.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author dzboy2k
 */
public class UserDao {
    public User checkLogin(String username, String password) throws Exception{
        String sql = "select * from users where username = ? and password = ?";
        try (
                Connection con = DatabaseUtils.openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()){
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                
                return user;
            }
            
        }
        return null;
    }
}
