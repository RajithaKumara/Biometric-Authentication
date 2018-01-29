/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author rajitha
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConn {
    
    private String dbUrl = "jdbc:derby:Database/db;create=true;user=user;password=user";
    private String table = "USERS";
    private Connection conn = null;
    
    public DbConn() throws SQLException{
        createConnection();
        Statement stmt=conn.createStatement();
        if(!conn.getMetaData().getSchemas().next()){
            stmt.execute("create schema db");
            System.out.println("create schema");
        }
        if(!conn.getMetaData().getTables(null, null, "USERS", null).next()){
            stmt.execute("CREATE TABLE USERS(ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (INCREMENT BY 1),NAME VARCHAR(100),EYE_WHITE_RATIO VARCHAR(100),EYE_BLACK_RATIO VARCHAR(100),SYMMETRIC_FAC_RATIO VARCHAR(100))");
            System.out.println("create table");
        }
        System.out.println(conn.getMetaData().getURL());
    }
    
    private void createConnection(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection(dbUrl);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void registerUser(String userName,String eyeWhiteRatio, String eyeBlackRatio, String symmetricFacRatio){
        try{
            PreparedStatement preStmt = conn.prepareStatement("INSERT INTO "+table
            +"(NAME,EYE_WHITE_RATIO,EYE_BLACK_RATIO,SYMMETRIC_FAC_RATIO) VALUES"
            +"(?,?,?,?)");

            preStmt.setString(1,userName);
            preStmt.setString(2,eyeWhiteRatio);
            preStmt.setString(3,eyeBlackRatio);
            preStmt.setString(4,symmetricFacRatio);

            preStmt.executeUpdate();
        }
        catch (SQLException ex){
           System.out.println(ex);
        }
    }

    public List<String> getUser(String userName) throws SQLException{
        List<String> user = new ArrayList<>();
        PreparedStatement preStmt = conn.prepareStatement("SELECT * FROM USERS WHERE NAME =?");
        preStmt.setString(1, userName);
        ResultSet rs = preStmt.executeQuery();
        while(rs.next()){
            String eyeWhiteRatio = rs.getString(3);
            String eyeBlackRatio = rs.getString(4);
            String symmetricRatio = rs.getString(5);
            user.add(eyeWhiteRatio);
            user.add(eyeBlackRatio);
            user.add(symmetricRatio);
        }
        return user;
    }

}
