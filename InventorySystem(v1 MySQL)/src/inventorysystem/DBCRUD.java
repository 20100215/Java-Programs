/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

/**
 *
 * @author Melva
 */
import java.sql.*;

public class DBCRUD {

    private static Connection con = null;

    public DBCRUD(String db, String user, String pass) {
        /*Connects to the database with user-supplied usernames and passwords (configured ahead in database previleges)*/
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, pass);
        } catch (Exception e) {
            /*Incorrect login credentials*/
            System.out.println(e);
        }
    }

    public DBCRUD() {
    }

    public boolean checkCon() {
        return (con != null) ? true : false;
    }

    public ResultSet displayItems() {
        /*Selects all records from the database*/
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from item_records");
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet displayItems(String orderBy) {
        /*Selects all records from the database with a particular order of items*/
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from item_records ORDER BY " + orderBy);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchItems(String text) {
        /*Selects records whose contents match a given search query (any column)*/
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from item_records WHERE name LIKE '%"+text+"%' OR brand LIKE '%"+text+"%' OR category LIKE '%"+text+"%'");
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet displayCritical() {
        /*Selects records whose quantity remaining is less than quantity required to maintain*/
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from item_records WHERE qtyRemaining < qtyMaintain");
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void insertItem(String query) {
        /*Inserts a new item record to the database*/
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateItem(String query) {
        /*Inserts a new item record to the database*/
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void deleteItem(int id) {
        /*Removes an existing item record from the database table*/
        String ret = "";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `item_records` WHERE ID=" + id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
