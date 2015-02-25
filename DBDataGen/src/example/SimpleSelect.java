package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleSelect {
  public static void main(String[] args) {
    try {
      Connection con=null;
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con=DriverManager.getConnection(
        "jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","youruserid",
       "yourpassword");
      System.out.println("connection successfully made.");
      Statement s=con.createStatement();
      String q;
      ResultSet result;
      q = "select X.course_id, Y.course_id as Prereq1, Z.course_id as Prereq, Z.prereq_id as Prereq from dbcourse.prereq X, dbcourse.prereq Y, dbcourse.prereq Z where Z.course_id = Y.prereq_id and Y.course_id = X.prereq_id";
      result = s.executeQuery(q);
      if (!result.next()) System.out.println ("Empty result.");
      else {
        do {
          System.out.println (result.getString("Prereq1") + " " + result.getString("Prereq"));
        } while (result.next());
      }
      s.close();
      con.close();
   } catch(Exception e){e.printStackTrace();}
 }
}