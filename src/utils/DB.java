/*
 * Property of Okami�
 * Not destined for commercial use
 */
package utils;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author 
 */
public class DB {
    
    public String url="jdbc:mysql://localhost:3306/steg";
    public String login="root";
    public String pwd="";
    Connection c;
    public static DB instance;
    
    private DB() {
        try {
            c = DriverManager.getConnection(url, login, pwd);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public Connection getConnection() {
        return c;
    }
    
    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }
    
}
