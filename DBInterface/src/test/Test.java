/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import backend.Value;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Test {
    private static ArrayList<String[]> data;
    public static void main(String[] args) {
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection
                    ("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","dik214",
                    "P826703900");
            String query = "select * from customer";
            Value e = new Value(con, query);
            String[] titles = e.getMeta();
            ArrayList<String[]> d = e.getData();
            for(int j = 0; j < titles.length; j++) {
                System.out.printf("%20s\t",titles[j]);
            }
            System.out.println();
            for(int i = 0; i < d.size(); i++) {
                for(int j = 0; j < d.get(i).length; j++) {
                    System.out.printf("%20s\t",d.get(i)[j]);
                }
                System.out.println();
            }
        }
//        catch(ClassNotFoundException e) {}
        catch(Exception e) {e.printStackTrace();}
    }
}
