/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnect;

/**
 *
 * @author water
 */
//import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static javax.management.Query.value;


/**
 *
 * @author sqlitetutorial.net
 */
public class DatabaseConnect
{
    private Connection conn = null;

    public static void main(String args[])
    {
        DatabaseConnect conn = new DatabaseConnect();
        
        /*
        conn.createTable(".\\src\\DDLs\\addressDDL.txt");
        conn.createTable(".\\src\\DDLs\\customerDDL.txt");
        conn.createTable(".\\src\\DDLs\\downloadDDL.txt");
        conn.createTable(".\\src\\DDLs\\movieDDL.txt");
        conn.createTable(".\\src\\DDLs\\movieFormatDDL.txt");
        conn.createTable(".\\src\\DDLs\\overdueDDL.txt");
        conn.createTable(".\\src\\DDLs\\rentalDDL.txt");
        */
        
        conn.insert();
        //conn.select();
        //conn.update();
        if (conn != null)
        {
            conn.close();            
        }            
    }

    public DatabaseConnect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:aqa_movie.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public void close() 
    {
        try
        {
            conn.close();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createTable(String ddlPath)
    {
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            
            Path ddlCreate = Paths.get(ddlPath);
            Stream<String> lines = Files.lines(ddlCreate);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            
            String sql = data;
            stmt.executeUpdate(sql);         
            stmt.close();           
            conn.commit();
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return stmt != null;
    }

    public boolean insert(String details, String CSVpath)
    {
        boolean bInsert = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            
            String sql = "INSERT INTO " + details;
            BufferedReader br = new BufferedReader(new FileReader(CSVpath));
            String line;
            while((line = br.readLine()) != null)
            {
                String[] values = line.split(",");
                stmt.executeUpdate(sql);
            }

            
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            bInsert = true;

        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    public boolean select()
    {
        boolean bSelect = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM COMPANY;");

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");

                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("AGE = " + age);
                System.out.println("ADDRESS = " + address);
                System.out.println("SALARY = " + salary);
                System.out.println();
            }
            
            rs.close();
            stmt.close();   
            bSelect = true;
        } 
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }
    
    public boolean update()
    {
        boolean bUpdate = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            bUpdate = true;
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bUpdate;
    }
}
