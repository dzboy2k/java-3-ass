/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.poly.app.helpers;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author dzboy2k
 */
public class DatabaseHelper {
    public static Connection openConnection() throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost;database=PolyApp;encrypt=true;trustServerCertificate=true;";
        String dbusername = "sa";
        String password = "Abc12345!";
        Connection con = DriverManager.getConnection(connectionUrl, dbusername, password);
        return con;
    }
}
