/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author DK
 */
public class Update {
    private Connection con;
    private Statement stat;
    private String query;
    
    public Update(Connection c, String q) throws SQLException {
        this.con = c;
        this.stat = con.createStatement();
        this.query = q;
    }
    
    public int execute() throws SQLException {
        int i = stat.executeUpdate(query);
        stat.close();
        return i;
    }
}
