/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnect;

/**
 *
 * @author rayan
 */
//import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.Arrays;

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
        
        
        conn.insert("Address", ".\\src\\CSVs\\Address.csv");
        conn.insert("Customer", ".\\src\\CSVs\\Customer.csv");
        conn.insert("Download", ".\\src\\CSVs\\Download.csv");
        conn.insert("Movie", ".\\src\\CSVs\\Movie.csv");
        conn.insert("MovieFormat", ".\\src\\CSVs\\MovieFormat.csv");
        conn.insert("Overdue", ".\\src\\CSVs\\Overdue.csv");
        conn.insert("Rental", ".\\src\\CSVs\\Rental.csv");
        */
        
        //conn.select();
        //conn.update();
        String[] columnNames = selectColumnNames("Customer");
        for(String names : columnNames)
        {
            System.out.print(names + ", ");
        }
        
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
    
    public boolean insert(String tableName)
    {
        boolean bInsert = false;
        Statement stmt = null;
        
        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
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
    
    public boolean insert(String csvName, String CSVpath)
    {
        boolean bInsert = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            long lineCount = 0;
            try (Stream<String> stream = Files.lines(Paths.get(CSVpath), StandardCharsets.UTF_8)) 
            {
                lineCount = stream.count();
            }
            
            String sql = "INSERT INTO " + csvName;
            BufferedReader br = new BufferedReader(new FileReader(CSVpath));
            String line;
            int lineNum = 0;
            String[] headers = null;
            String[][] values = new String[(int)lineCount - 1][];
            
            while((line = br.readLine()) != null)
            {
                if(lineNum == 0)
                {
                    headers = line.split(",");
                }
                else
                {
                    values[lineNum - 1] = line.split(",");
                }
                
                lineNum++;
            }
                        
            for(int i = 0; i < headers.length; i++)
            {
                if(i == 0)
                {
                    sql += " (" + headers[i] + ",";
                }
                else if(i == headers.length - 1)
                {
                    sql += headers[i] + ") VALUES (";
                }
                else
                {
                    sql += headers[i] + ",";
                }
            }
                        
            String sqlTemp = "";
            for(int j = 0; j < lineCount - 1; j++)
            {
                for(int i = 0; i < values[j].length; i++)
                {
                    if(isNumeric(values[j][i]))
                    {
                        if(i == values[j].length - 1)
                        {
                            sqlTemp += values[j][i] + ")";
                        }
                        else
                        {
                            sqlTemp += values[j][i] + ",";
                        }
                    }
                    else
                    {
                        if(i == values[j].length - 1)
                        {
                            sqlTemp += "'" + values[j][i] + "')";
                        }
                        else
                        {
                            sqlTemp += "'" + values[j][i] + "',";
                        }
                    }
                }
                
                System.out.println(sql + sqlTemp);
                stmt.executeUpdate(sql + sqlTemp);
                sqlTemp = "";
            }
            
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

    public static String[] selectColumnNames(String tableName)
    {
        Statement stmt = null;
        ResultSet rs;
        String[] name = null;
        
        try
        {
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++ )
            {
                name[i] = rsmd.getColumnName(i);
            }
            
            rs.close();
            stmt.close();   
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
        return name;
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
    
    public boolean isNumeric(String str)
    {
        if(str.equals(null))
            return false;
        
        final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        /*
        -?          determines if number starts with minus
        \d+         matches one or more digits
        (\.\d+)?    matches for decimal point and digits following
        */
        
        return pattern.matcher(str).matches();
    }
}