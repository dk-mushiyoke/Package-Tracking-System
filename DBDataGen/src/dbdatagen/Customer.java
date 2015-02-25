/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbdatagen;

import java.io.BufferedReader;
import java.io.FileReader;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Di Kong <dik214@lehigh.edu>
 */
public class Customer {

    /**
     * @param args the command line arguments
     */
    public static final int COUNT = 50, IDDIGIT = 6;
    public static void customerGen() {
        // TODO code application logic here
        BufferedReader fname, lname;
        ArrayList<String> firstN = new ArrayList(),
                          lastN = new ArrayList();
        int id = (int)Math.pow(10,IDDIGIT - 1);
        try {
            fname = new BufferedReader(new FileReader("firstName.txt"));
            lname = new BufferedReader(new FileReader("lastName.txt"));
            String line;
            while((line = fname.readLine()) != null) {firstN.add(line);}
            while((line = lname.readLine()) != null) {lastN.add(line);}
            fname.close();
            lname.close();
        } catch(Exception e){e.printStackTrace();}
        for(int j = 0; j < COUNT; j++){
            id ++;
            String n = nameGen(firstN,lastN);
            long i = phoneGen();
            System.out.printf("insert into customer values(%d,%20s,%10d);",id,n,i);
        }
    }
    
    private static long phoneGen() {
        long num = (long)(Math.random() * 9999999999L + 1);
        while (num < 1000000000) {num *= 10;}
        return num;
    }
    
    private static String nameGen(ArrayList f, ArrayList l) {
        int f_index = (int)(Math.random() * (f.size()-1) + 1),
            l_index = (int)(Math.random() * (l.size()-1) + 1);
        String fname = (String)f.get(f_index),
               lname = (String)l.get(l_index);
        String fullName = fname + " " + lname;
        return fullName;
    }
    
}
