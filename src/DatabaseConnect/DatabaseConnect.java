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
import dataStructures.hashmap.hashEntry;
import dataStructures.hashmap.hashMap;
import java.util.Iterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 *
 * @author sqlitetutorial.net
 */
public class DatabaseConnect
{
    private static Connection conn = null;
    
    public static void main(String args[])
    {
        DatabaseConnect conn = new DatabaseConnect();
        
        Scanner scnr = new Scanner(System.in);
        int mainChoice = 0;
        String input = null;
        String temp = null;
        
        hashMapTest();
        
        while(true)
        {
            System.out.println("Please input what you would like to do (required inputs in brackets)");
            System.out.println("\n\t1 - create table with DDL (path to DDL)\n\t2 - insert data from CSV (table name, path to CSV)\n\t3 - insert data via user input (table name)\n\t4 - update a field in the table(table name)\n\t5 - delete a row/customer (table name)\n\t6 - display overdue fees (path to query)\n\t7 - auto input (runs through a test case)\n\t8 - exit the program");
            if(mainChoice < 1 || mainChoice > 7)
            {
                mainChoice = scnr.nextInt();
                scnr.nextLine();
            }
            else
            {
                System.out.println("Input failed");
                continue;
            }
            
            switch(mainChoice)
            {
                case 1:
                    System.out.println("Please input the path to the DDL (to create the table)\n\tuse the example notation .\\src\\DDLs\\customerDDL.txt");
                    input = scnr.nextLine();
                    conn.createTable(input);
                    break;
                case 2:
                    System.out.println("Please input the table name (to insert the data from a csv");
                    input = scnr.nextLine();
                    System.out.println("Please input the path to the CSV (to insert the CSV data into the table)\n\tuse the example notation .\\src\\CSVs\\Customer.csv");
                    temp = scnr.nextLine();
                    conn.insert(input, temp);
                    break;
                case 3:
                    System.out.println("Please input the table name you want to manually insert data into");
                    input = scnr.nextLine();
                    conn.insert(input);
                    break;
                case 4:
                    System.out.println("Please input the table name you want to manually update data for");
                    input = scnr.nextLine();
                    conn.update(input);
                    break;
                case 5:
                    System.out.println("Please input the table name you want to delete a row/customer for");
                    input = scnr.nextLine();
                    conn.delete(input);
                    break;
                case 6:
                    System.out.println("Please input the path to the query file\n\tuse the example notation .\\src\\Queries\\overdue_report.txt");
                    input = scnr.nextLine();
                    conn.displayOverdueFees(input);
                    break;
                case 7:
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



                    String[] columnNames = selectColumnNames("Customer");
                    for(String names : columnNames)
                    {
                        System.out.print(names + ", ");
                    }


                    conn.insert("Customer");
                    conn.update("Customer");
                    conn.delete("Customer");
                    conn.displayOverdueFees(".\\src\\Queries\\overdue_report.txt");
                    break;
                case 8:
                    if (conn != null)
                    {
                        conn.close();            
                    }
                    System.exit(0);
                default:
                    continue;
            }
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
        ResultSet rs = null;
        
        try
        {
            stmt = conn.createStatement();
            
            System.out.println("The fields in the selected table are:");
            String[] columnNames = selectColumnNames(tableName);
            String columnNamesConcat = "";
            for(int i = 0; i < columnNames.length; i++)
            {
                System.out.print("\n|\n=---" + columnNames[i]);
                if(i == (columnNames.length - 1))
                    columnNamesConcat += columnNames[i];
                else
                    columnNamesConcat += columnNames[i] + ",";
            }
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            int rowCount = rs.getInt(1);
            
            System.out.println("\n");
            Scanner scnr = new Scanner(System.in);
            String sql = "INSERT INTO " + tableName + "(" + columnNamesConcat + ") VALUES (" + (rowCount + 1) + ",";
            //(rowCount + 1) is the new "column"ID
            //this assumes we aren't adding into what should be static .db (like MovieFormat)
            String tmp = null;
            for(int i = 1; i < columnNames.length; i++)
            {
                System.out.println("Please enter the value you want to insert into " + columnNames[i]);
                tmp = scnr.nextLine();
                if(i == (columnNames.length - 1))
                {
                    if(isNumeric(tmp))
                        sql += tmp + ")";
                    else
                        sql += "'" + tmp + "')";
                }
                else
                {
                    if(isNumeric(tmp))
                        sql += tmp + ",";
                    else
                        sql += "'" + tmp + "',";
                }
            }
            stmt.execute(sql);
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
            String[] headers = null;    //can replace with function coded
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
    
    public boolean update(String tableName)
    {
        boolean bUpdate = false;
        Statement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = conn.createStatement();
            
            String[] columnNames = selectColumnNames(tableName);
            
            String sql = "";
            Scanner scnr = new Scanner(System.in);
            System.out.println("\nPlease input the " + columnNames[0] + " that you want to change: ");
            int id = scnr.nextInt();
            scnr.nextLine();
            System.out.println("Please input the EXACT name of the field above you want to modify: ");
            String field = scnr.nextLine();
            System.out.println("Please enter what you would like to change " + field + " to: ");
            String choice = scnr.nextLine();
            if(!isNumeric(choice))
                sql = "UPDATE " + tableName + " set " + field + " = '" + choice + "' WHERE " + columnNames[0] + "=" + id;
            else
                sql = "UPDATE " + tableName + " set " + field + " = " + choice + " WHERE " + columnNames[0] + "=" + id;
            System.out.println(sql);
            stmt.execute(sql);
            stmt.close();
            conn.commit();
            bUpdate = true;
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bUpdate;
    }
    
    public boolean delete(String tableName)
    {
        boolean bDelete = false;
        Statement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = conn.createStatement();
            
            System.out.println("The fields in the selected table are:");
            String[] columnNames = selectColumnNames(tableName);
            for(int i = 0; i < columnNames.length; i++)
            {
                System.out.print("\n|\n=---" + columnNames[i]);
            }
            
            String sql = "";
            Scanner scnr = new Scanner(System.in);
            System.out.println("\nPlease input the " + columnNames[0] + " that you want to remove the data for: ");
            int id = scnr.nextInt();
            sql = "DELETE FROM " + tableName + " WHERE " + columnNames[0] + "=" + id;
            System.out.println(sql);
            stmt.execute(sql);
            stmt.close();
            conn.commit();
            bDelete = true;
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bDelete;
    }
    
    public boolean displayOverdueFees(String queriesPath)
    {
        boolean bDisplay = false;
        Statement stmt = null;
        ResultSet rs = null;
        String tableName = "Customer";
        String sql = "";
        
        try
        {
            stmt = conn.createStatement();
            
            System.out.println("The fields in the selected table are:");
            String[] columnNames = selectColumnNames(tableName);
            
            Path ddlCreate = Paths.get(queriesPath);
            Stream<String> lines = Files.lines(ddlCreate);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            
            Scanner scnr = new Scanner(System.in);
            System.out.println("\nPlease input the " + columnNames[0] + " that you want to find the overdue fees for: ");
            int id = scnr.nextInt();
            
            sql = "CREATE VIEW overdueFees AS" + data + " AND " + columnNames[0] + "=" + id;
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
                
            rs.close();
            stmt.close();
            conn.commit();
            bDisplay = true;
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bDisplay;
    }
    
    public static String[] selectColumnNames(String tableName)
    {
        Statement stmt = null;
        ResultSet rs = null;
        String[] columnName = null;
        
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            columnName = new String[count];

            for (int i = 1; i <= count; i++)
            {
               columnName[i - 1] = metaData.getColumnLabel(i);
            }
            
            rs.close();
            stmt.close();   
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
        return columnName;
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
    
    public static void hashMapTest()
    {
        hashMap<String, String> studentMap = new hashMap<>();
        hashMap<String, String> studentNewMap;
        
        studentMap     =   studentMap.add("vikash", "Singh");

        studentNewMap  =   studentMap.add("Vickey", "Singh");

        studentNewMap  =   studentNewMap.add("Xyz","singh");

        //replacing the existing key value in studentNewMap
        studentNewMap  =   studentNewMap.add("vikash","kumar");

        Iterator studentMapIterator = studentMap.iterator();

        Iterator studentNewMapIterator = studentNewMap.iterator();

        System.out.println("printing all value of the studentMap\n");

        while(studentMapIterator.hasNext())
        {
            //remember type casting
            hashEntry<String,String> hashTemp = (hashEntry)studentMapIterator.next();
            System.out.println(hashTemp.getKey() + " " + hashTemp.getValue());

        }

        System.out.println("\nprinting all value of the studentNewMap\n");

        while(studentNewMapIterator.hasNext())
        {
            //remember type casting
            hashEntry<String,String> hashTemp = (hashEntry)studentNewMapIterator.next();
            System.out.println(hashTemp.getKey() + " " + hashTemp.getValue());

        }

        //removing the entry in studentNewMap
        studentNewMap = studentNewMap.remove("vikash");

        studentNewMapIterator = studentNewMap.iterator();

        System.out.println("\nprinting all value of the studentNewMap after removing\n");


        while(studentNewMapIterator.hasNext())
        {
            //remember type casting
            hashEntry<String,String> hashTemp = (hashEntry)studentNewMapIterator.next();
            System.out.println(hashTemp.getKey() + " " + hashTemp.getValue());

        }

        studentMapIterator = studentMap.iterator();

        System.out.println("printing all value of the studentMap\n");

        while(studentMapIterator.hasNext())
        {
            //remember type casting
            hashEntry<String,String> hashTemp = (hashEntry)studentMapIterator.next();
            System.out.println(hashTemp.getKey() + " " + hashTemp.getValue());

        }
    }
}