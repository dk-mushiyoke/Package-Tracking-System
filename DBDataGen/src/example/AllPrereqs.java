package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AllPrereqs {
  public static void main(String[] args) {
    try {
      Connection con=null;
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con=DriverManager.getConnection(
        "jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","youruserid",
       "yourpassword");
      Statement s=con.createStatement();
      String q;
      int c;
      ResultSet result;
      int maxCourse = 0;
      q = "select count(*) as C from course";
      result = s.executeQuery(q);
      if (!result.next()) System.out.println ("Unexpected result. Empty result.");
      else {
        maxCourse = Integer.parseInt(result.getString("C"));
        if (result.next()) System.out.println ("Unexpected result.  Multiple count rows.");
      }
      int numCourse = 0, oldNumCourse = -1;
      int[] prereqs = new int [maxCourse];
      Scanner krb = new Scanner(System.in);
      System.out.print("Input a course id (number): ");
      int course = krb.nextInt();
      String courseString ="" + course;
      while (numCourse != oldNumCourse) {
        for (int i = oldNumCourse + 1; i < numCourse; i++) {
          courseString += ", " + prereqs[i];
        }
        oldNumCourse = numCourse;
        q = "select prereq_id from prereq  where course_id in (" + courseString + ")";
        result = s.executeQuery(q);
        while (result.next()) {
          c = result.getInt("prereq_id");
          boolean found = false;
          for (int i = 0; i < numCourse; i++) found |= prereqs[i]==c;
          if (!found) prereqs[numCourse++] = c;
        }
        courseString = "" + prereqs[oldNumCourse];
      }
      s.close();
      con.close();
      System.out.print("The courses that must be taken prior to " + course + " are: ");
      for (int i = 0; i < numCourse; i++) System.out.print ((i==0?" ":", ") + prereqs[i]);
      System.out.println();
   } catch(Exception e){e.printStackTrace();}
 }
}
