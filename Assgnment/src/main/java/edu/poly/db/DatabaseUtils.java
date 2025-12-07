/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author dzboy2k
 */
public class DatabaseUtils {
    public static Connection openConnection() throws Exception{
        String connectionURL = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=AssignmentDB;encrypt=true;trustServerCertificate=true;";
        
        // Declare the JDBC objects.
        Connection con = null;
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(connectionURL, "sa", "Abc12345!");
        
        return con;
    }
}
