package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleInsert {
  public static void main(String[] args) {
    try {
      Connection con=null;
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con=DriverManager.getConnection(
        "jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","youruserid",
       "yourpassword");
      Statement s=con.createStatement();
      String q;
      ResultSet result;
      int i;
      q = "insert into enemies2 select name, salary/10000 from instructor";
      i = s.executeUpdate(q);
      System.out.println ("value returned: " + i);
      s.close();
      con.close();
   } catch(Exception e){e.printStackTrace();}
 }
}