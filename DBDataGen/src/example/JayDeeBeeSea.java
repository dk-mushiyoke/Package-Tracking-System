package example;

import java.io.IOException;
import java.sql.*;

class JayDeeBeeSea {
  public static void main (String[] arg) 
  throws SQLException, IOException, java.lang.ClassNotFoundException {
    Class.forName ("oracle.jdbc.driver.OracleDriver");
    Connection con = DriverManager.getConnection
      ("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","youruserid",
       "yourpassword");
      Statement s=con.createStatement();
      String q;
      ResultSet result;
      q = "select title, dept_name from course";
      result = s.executeQuery(q);
      if (!result.next()) System.out.println ("Empty result.");
      else {
        do {
          System.out.println (result.getString("title") + " " + result.getString("dept_name"));
        } while (result.next());
      }
      s.close();
      con.close();
  }
}