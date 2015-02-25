/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Value {
    private Connection con;
    private Statement stat;
    private String query;
    private String[] meta;
    private ArrayList<String[]> data;
    
    public Value(Connection c, String q) throws SQLException {
        this.con = c;
        this.stat = con.createStatement();
        this.query = q;
        dataInit();
    }
    
    private void dataInit() throws SQLException {
        ResultSet result = stat.executeQuery(query);
        ResultSetMetaData rsmd = result.getMetaData();
        data = new ArrayList();
        meta = new String[rsmd.getColumnCount()];
        for(int i = 1; i <= rsmd.getColumnCount(); i++)
            meta[i-1] = rsmd.getColumnName(i);
        int colCount = rsmd.getColumnCount();
        String[] row;
        if (!result.next())
            throw new IndexOutOfBoundsException("Empty ResultSet");
        do {
            row = new String[colCount];
            for(int i = 0; i < colCount; i++)
                row[i] = result.getString(meta[i]);
            data.add(row);
        } while (result.next());
        stat.close();
    }
    
    public String[] getMeta() {
        return meta;
    }
    
    public ArrayList<String[]> getData() {
        return data;
    }
    
    public void printAll() throws SQLException {
        for(int j = 0; j < meta.length; j++) {
            System.out.printf("%-20s\t",meta[j]);
        }
        System.out.println();
        for(int i = 0; i < data.size(); i++) {
            for(int j = 0; j < data.get(i).length; j++) {
                if(data.get(i)[j] != null)
                    System.out.printf("%-20s\t",data.get(i)[j]);
                else
                    System.out.printf("%-20s\t"," ");
            }
            System.out.println();
        }
    }
}
