/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author DK
 */
public class Menu {
    public static void main(String[] args) {
        System.out.println("*** Korth Package Info Database System v1.0.0 ***");
        System.out.println("Author:  Di Kong");
        System.out.println("Email:   DIK214 at LEHIGH dot EDU");
        System.out.println("Purpose: This program is a project for the course CSE 341");
        System.out.println("         Commandline version");
        Connection con = null;
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection
                    ("jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","dik214",
                    "P826703900");
        }
        catch(ClassNotFoundException e) {System.out.println("Cannot find JDBC driver. System exiting..."); System.exit(0);}
        catch(SQLException e) {System.out.println("Connection cannot establish. Please check your internet connection."); System.exit(0);}
        Scanner in = new Scanner(System.in);
        try{
            displayMenu(in,con);
            con.close();
        }
        catch(SQLException e) {System.out.println("Operation failed. Please check your input."); System.exit(0);}
        System.out.println("Thank you for using my system. Have a good one.");
    }
    
    public static void displayMenu(Scanner in, Connection con) throws SQLException {
        String choice = null,s;
//        while(true) {
        System.out.println();
        System.out.println("Please select your title: ");
        System.out.println(" 0 - System admin");
        System.out.println(" 1 - Customer relation manager");
        System.out.println(" 2 - Tracking manager");
        System.out.println(" 3 - Accounting manager");
        System.out.println(" 4 - Package manager");
        System.out.println(" 5 - Customer");
        System.out.println("\n 9 - Exit program");
        System.out.print("Enter your choice : ");
        choice = in.next();
        s = in.nextLine();
        switch(choice) {
            case "0":
                System.out.println("Dear sys admin, you should be writing sql code instead of trolling around.");
                break;
            case "1":
                cusManMenu(in,con);
                break;
            case "2":
                trkManMenu(in,con);
                break;
            case "3":
                acctManMenu(in,con);
                break;
            case "4":
                pkgManMenu(in,con);
                break;
            case "5":
                customer(in,con);
                break;
            case "9":
                break;
            default:
                System.out.println("Unrecognized input. System exiting....");
                break;
        }
//        }
    }
    
    public static void customer(Scanner in, Connection con) throws SQLException{
        String name[] = new String[1];
        int id = getCustomerIdName(in,con,name);
        while(true) {
            System.out.println("Welcome, customer " + id + ", " + name[0] + ".");
            System.out.println();
            System.out.println("Please choose your action.");
            System.out.println(" 0 - All packages associate with user");
            System.out.println(" 1 - Current package status");
            System.out.println(" 2 - Package from adderss");
            System.out.println(" 3 - Package to address");
            System.out.println(" 4 - Package shipping price");
            System.out.println(" 5 - Package tracking history");
            System.out.println(" 6 - Package information");
            System.out.println(" 7 - Customer information");
            System.out.println("\n 9 - Exit program");
            System.out.print("Enter your choice : ");
            String choice = in.next();
            String s = in.nextLine();
            String query = "select sender.pid as package_id,sender.cid as sender_id,c1.name as sender_name,receiver.cid as receiver_id,c2.name as receiver_name "
                    + "from sender,receiver,customer c1,customer c2 "
                    + "where sender.pid = receiver.pid and sender.cid = c1.cid and receiver.cid = c2.cid and "
                    + "(sender.cid = " + id + " or receiver.cid = " + id + ")";
            Value associatePkg = new Value(con,query);
            ArrayList<String[]> ap = associatePkg.getData();
            switch(choice) {
                case "0":
                    System.out.println("A list of all packages associated with you is displayed below.");
                    associatePkg.printAll();
                    break;
                case "1":
                    System.out.print("Enter the package id you wish to look up: ");
                    String pid = in.next();
                    s = in.nextLine();
                    boolean found = false;
                    for(String[] ele:ap) {
                        if(pid.equals(ele[0])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("This package does not associate with you. You cannot get its info.");
                        break;
                    }
                    query = "select tnum as tracking_number,time,activity,street,city,state,zip,country "
                            + "from tracking natural join package, location "
                            + "where tracking.lid = location.lid and pid = " + pid + " and time >= all(select time from tracking where pid = " + pid + ")";
                    Value v = new Value(con, query);
                    System.out.println("The current activity of your package is displayed below.");
                    v.printAll();
                    break;
                case "2":
                    System.out.print("Enter the package id you wish to look up: ");
                    pid = in.next();
                    s = in.nextLine();
                    found = false;
                    for(String[] ele:ap) {
                        if(pid.equals(ele[0])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("This package does not associate with you. You cannot get its info.");
                        break;
                    }
                    query = "select street,city,state,zip,country from sender where pid = " + pid;
                    v = new Value(con, query);
                    System.out.println("The from address of your package is displayed below.");
                    v.printAll();
                    break;
                case "3":
                    System.out.print("Enter the package id you wish to look up: ");
                    pid = in.next();
                    s = in.nextLine();
                    found = false;
                    for(String[] ele:ap) {
                        if(pid.equals(ele[0])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("This package does not associate with you. You cannot get its info.");
                        break;
                    }
                    query = "select street,city,state,zip,country from receiver where pid = " + pid;
                    v = new Value(con, query);
                    System.out.println("The from address of your package is displayed below.");
                    v.printAll();
                    break;
                case "4":
                    System.out.print("Enter the package id you wish to look up: ");
                    pid = in.next();
                    s = in.nextLine();
                    found = false;
                    for(String[] ele:ap) {
                        if(pid.equals(ele[0])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("This package does not associate with you. You cannot get its info.");
                        break;
                    }
                    String price;
                    query = "select price from regular_price where pid = " + pid;
                    try {price = new Value(con, query).getData().get(0)[0];}
                    catch(IndexOutOfBoundsException e) {
                        System.out.println("You have negotiated a special rate with us.");
                        query = "select price from negotiated_price where cid = ("
                                + "select customer.cid from payment natural join transaction, account, customer "
                                + "where transaction.aid = account.aid and account.cid = customer.cid and pid = " + pid + ")";
                        price = new Value(con, query).getData().get(0)[0];
                    }
                    System.out.println("The price of your package is $" + price);
                    break;
                case "5":
                    System.out.print("Enter the package id you wish to look up: ");
                    pid = in.next();
                    s = in.nextLine();
                    found = false;
                    for(String[] ele:ap) {
                        if(pid.equals(ele[0])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("This package does not associate with you. You cannot get its info.");
                        break;
                    }
                    query = "select time, activity, type as location_type,street,city,state,zip,country "
                            + "from tracking natural join package, location "
                            + "where tracking.lid = location.lid and pid = " + pid
                            + "order by time";
                    v = new Value(con, query);
                    System.out.println("The detailed tracking history is displayed below.");
                    v.printAll();
                    break;
                case "6":
                    System.out.print("Enter the package id you wish to look up: ");
                    pid = in.next();
                    s = in.nextLine();
                    found = false;
                    for(String[] ele:ap) {
                        if(pid.equals(ele[0])) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("This package does not associate with you. You cannot get its info.");
                        break;
                    }
                    query = "select s.pid as package_id, s.cid as sender_id, r.cid as receiver_id, weight, speed "
                            + "from sender s join receiver r on s.pid = r.pid, package "
                            + "where (s.pid = " + pid + " or r.pid = " + pid + ") and package.pid = " + pid;
                    v = new Value(con, query);
                    System.out.println("The package info is displayed below.");
                    v.printAll();
                    break;
                case "7":
                    query = "select * from customer where cid = " + Integer.toString(id);
                    v = new Value(con, query);
                    System.out.println("Your info is displayed below.");
                    v.printAll();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Unrecognized input. Try again");
                    continue;
            }
            
            System.out.print("\nDo you wish to continue (Y/N): ");
            s = in.nextLine();
            if(s.compareToIgnoreCase("Y") != 0 && s.compareToIgnoreCase("YES") != 0)
                return;
        }
    }
    
    public static void cusManMenu(Scanner in, Connection con) throws SQLException {
        while(true) {
            System.out.println("Welcome, customer relation manager.");
            System.out.println();
            System.out.println("Please choose your action.");
            System.out.println(" 0 - Get customer list");
            System.out.println(" 1 - Get customer detail");
            System.out.println(" 2 - Add a new customer");
            System.out.println(" 3 - Delete an existing customer");
//            System.out.println(" 4 - Edit customer detail");
            System.out.println("\n 9 - Previous menu");
            System.out.print("Enter your choice : ");
            String choice = in.next();
            String s = in.nextLine();
            String query;
            switch(choice) {
                case "0":
                    query = "select * from customer order by cid asc";
                    Value v = new Value(con, query);
                    System.out.println("A list of all customers is displayed below.");
                    v.printAll();
                    break;
                case "1":
                    int id = getCustomerId(in,con);
                    query = "select * from customer where cid = " + id;
                    v = new Value(con, query);
                    System.out.println("The customer detail is displayed below.");
                    v.printAll();
                    break;
                case "2":
                    int cid;
                    String name,phone = null,type;
                    System.out.println();
                    System.out.print("Enter the preferred user id of the customer: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid user id. Try enter again: ");
                        s = in.next();
                    }
                    cid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the name of the customer you wish to add: ");
                    name = in.nextLine();
                    System.out.print("Enter the phone number of the customer: ");
                    while(phone == null) {
                        phone = in.next();
                        s = in.nextLine();
                        for(char ch:phone.toCharArray())
                            if(!Character.isDigit(ch)) {
                                System.out.print("Not a valid phone number. Try enter again: ");
                                phone = null;
                            }
                        if(phone.length() != 10) {
                            System.out.print("Not a valid phone number. Try enter again: ");
                            phone = null;
                        }
                    }
                    System.out.print("Is the customer an enterprise? (Y/N) : ");
                    s = in.nextLine();
                    if(s.compareToIgnoreCase("Y") == 0 || s.compareToIgnoreCase("YES") == 0)
                        type = "enterprise";
                    else if(s.compareToIgnoreCase("N") == 0 || s.compareToIgnoreCase("NO") == 0)
                        type = "individual";
                    else {
                        System.out.println("Unrecognized input. Try enter again.");
                        continue;
                    }
                    
                    query = "insert into customer values(" + cid + ",'" + name + "'," + phone + ",'" + type + "')";
                    Update u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to add customer. Please contact your sysadmin");
                    else System.out.println("Add customer successful.");
                    break;
                case "3":
                    id = getCustomerId(in,con);
                    query = "delete from customer where cid = " + id;
                    u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to delete customer. Please check if the customer exists.");
                    else System.out.println("Delete customer successful.");
                    break;
//                case "4":
//                    I cannot get this to work. JDBC would hang forever when executing this part.
//                    At least I tried to do this...
                    
//                    id = getCustomerId(in,con);
//                    query = "select * from customer where cid = " + id;
//                    v = new Value(con, query);
//                    String[] column = v.getMeta();
//                    String title, newVal;
//                    while(true) {
//                        System.out.println("Please select the column which you would like to change");
//                        for(int i = 0; i < column.length; i++) {
//                            System.out.println(" " + i + " - " + column[i]);
//                        }
//                        System.out.print("Enter your choice: ");
//                        String ch = in.next();
//                        s = in.nextLine();
//                        int col;
//                        try {col = Integer.parseInt(ch);}
//                        catch(NumberFormatException e) {System.out.println("Not a valid column. Try enter again."); continue;}
//                        if(col < 0 || col >= column.length) System.out.println("Not a valid column. Try enter again.");
//                        else if(col == 0) System.out.println("ID is generated automatically. Editing is prohibited.");
//                        else {title = column[col];break;}
//                    }
//                    System.out.print("Please enter the new value for selected column: ");
//                    newVal = in.nextLine();
//                    query = "update customer set " + title + " = '" + newVal + "' where cid = " + id;
//                    System.out.println(query);
//                    u = new Update(con, query);
//                    if(u.execute() == 0) System.out.println("Fail to delete customer. Please check if the customer exists.");
//                    else System.out.println("Delete customer successful.");
//                    break;
                case "9":
                    return;
                default:
                    System.out.println("Unrecognized input. Try again");
                    continue;
            }
            
            System.out.print("\nDo you wish to continue (Y/N): ");
            s = in.nextLine();
            if(s.compareToIgnoreCase("Y") != 0 && s.compareToIgnoreCase("YES") != 0)
                return;
        }
    }
    
    public static void trkManMenu(Scanner in, Connection con) throws SQLException {
        while(true) {
            System.out.println("Welcome, tracking manager.");
            System.out.println();
            System.out.println("Please choose your action.");
            System.out.println(" 0 - Get tracking list");
            System.out.println(" 1 - Get tracking detail");
            System.out.println(" 2 - Add a new tracking record");
            System.out.println(" 3 - Delete an existing record");
            System.out.println("\n 9 - Previous menu");
            System.out.print("Enter your choice : ");
            String choice = in.next();
            String s = in.nextLine();
            String query;
            switch(choice) {
                case "0":
                    query = "select pid as package_id,tnum as tracking_number,time,activity,type as location_type,street,city,state,zip,country "
                            + "from tracking natural join location order by time asc";
                    Value v = new Value(con, query);
                    System.out.println("A list of all tracking records is displayed below.");
                    v.printAll();
                    break;
                case "1":
                    int id = getPackageId(in,con);
                    query = "select pid as package_id,tnum as tracking_number,time,activity,type as location_type,street,city,state,zip,country "
                            + "from tracking natural join location where pid = " + id + " order by time asc";
                    v = new Value(con, query);
                    System.out.println("The tracking history of the package is displayed below.");
                    v.printAll();
                    break;
                case "2":
                    Long tnum;
                    int pid,year,month,day,hour,minute,lid;
                    String activity;
                    System.out.println();
                    System.out.print("Enter the tracking number: ");
                    while(!in.hasNextLong()) {
                        System.out.print("Not a valid tracking number. Try enter again: ");
                        s = in.next();
                    }
                    tnum = in.nextLong();
                    s = in.nextLine();
                    System.out.print("Enter the package id of the tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid package id. Try enter again: ");
                        s = in.next();
                    }
                    pid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the year of tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid year. Try enter again: ");
                        s = in.next();
                    }
                    year = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the month of tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid month. Try enter again: ");
                        s = in.next();
                    }
                    month = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the day of tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid day. Try enter again: ");
                        s = in.next();
                    }
                    day = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the hour of tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid hour. Try enter again: ");
                        s = in.next();
                    }
                    hour = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the minute of tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid minute. Try enter again: ");
                        s = in.next();
                    }
                    minute = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the description of tracking activity: ");
                    activity = in.nextLine();
                    System.out.print("Enter the location id associated with the tracking record: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid location id. Try enter again: ");
                        s = in.next();
                    }
                    lid = in.nextInt();
                    s = in.nextLine();
                    query = "insert into tracking values(" + tnum + "," + pid + ",timestamp '" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00','" + activity + "'," + lid + ")";
                    Update u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to add tracking record. Please check your columns.");
                    else System.out.println("Add tracking record successful.");
                    break;
                case "3":
                    id = getPackageId(in, con);
                    query = "delete from tracking where pid = " + id;
                    u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to delete tracking record. Please check if the customer exists.");
                    else System.out.println("Delete tracking record successful.");
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Unrecognized input. Try again");
                    continue;
            }
            
            System.out.print("\nDo you wish to continue (Y/N): ");
            s = in.nextLine();
            if(s.compareToIgnoreCase("Y") != 0 && s.compareToIgnoreCase("YES") != 0)
                return;
        }
    }
    
    public static void acctManMenu(Scanner in, Connection con) throws SQLException {
        while(true) {
            System.out.println("Welcome, accounting manager.");
            System.out.println();
            System.out.println("Please choose your action.");
            System.out.println(" 0 - Get account list");
            System.out.println(" 1 - Get account details");
            System.out.println(" 2 - Add a new account");
            System.out.println(" 3 - Delete an existing account");
            System.out.println(" 4 - Add a new transaction record");
            System.out.println(" 5 - Delete an existing transaction record");
            System.out.println("\n 9 - Previous menu");
            System.out.print("Enter your choice : ");
            String choice = in.next();
            String s = in.nextLine();
            String query;
            switch(choice) {
                case "0":
                    query = "select aid as account_id,name as customer_name,account.cid as customer_id,account.type as account_type "
                            + "from account join customer on customer.cid = account.cid";
                    Value v = new Value(con, query);
                    System.out.println("A list of all accounts is displayed below.");
                    v.printAll();
                    break;
                case "1":
                    int id = getAccountId(in,con);
                    query = "select aid as account_id,name as customer_name,account.cid as customer_id,account.type as account_type,tid as transaction_id,time,amount "
                            + "from account natural join transaction,customer "
                            + "where account.cid = customer.cid and aid = " + id;
                    v = new Value(con, query);
                    System.out.println("The tracking history of the package is displayed below.");
                    v.printAll();
                    break;
                case "2":
                    int aid,cid;
                    String type;
                    System.out.println();
                    System.out.print("Enter the account id: ");
                    while(!in.hasNextLong()) {
                        System.out.print("Not a valid account id. Try enter again: ");
                        s = in.next();
                    }
                    aid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the owner's id of the account: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid customer id. Try enter again: ");
                        s = in.next();
                    }
                    cid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter type of account (credit/debit) : ");
                    type = in.nextLine();
                    query = "insert into account values(" + aid + "," + cid + ",'" + type + "')";
                    Update u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to add account record. Please check your columns.");
                    else System.out.println("Add account successful.");
                    break;
                case "3":
                    id = getAccountId(in, con);
                    query = "delete from account where aid = " + id;
                    u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to delete account. Please check if the customer exists.");
                    else System.out.println("Delete account successful.");
                    break;
                case "4":
                    int tid,year,month,day,hour,minute;
                    double amount;
                    System.out.println();
                    System.out.print("Enter the transaction id: ");
                    while(!in.hasNextLong()) {
                        System.out.print("Not a valid transaction id. Try enter again: ");
                        s = in.next();
                    }
                    tid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the account id of which the transaction is on: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid account id. Try enter again: ");
                        s = in.next();
                    }
                    aid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the year of transaction: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid year. Try enter again: ");
                        s = in.next();
                    }
                    year = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the month of transaction: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid month. Try enter again: ");
                        s = in.next();
                    }
                    month = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the day of transaction: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid day. Try enter again: ");
                        s = in.next();
                    }
                    day = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the hour of transaction: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid hour. Try enter again: ");
                        s = in.next();
                    }
                    hour = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the minute of transaction: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid minute. Try enter again: ");
                        s = in.next();
                    }
                    minute = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the amount of transaction: ");
                    while(!in.hasNextDouble()) {
                        System.out.print("Not a valid dollar amount. Try enter again: ");
                        s = in.next();
                    }
                    amount = in.nextDouble();
                    s = in.nextLine();
                    query = "insert into transaction values(" + tid + "," + aid + ",timestamp '" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00'," + amount + ")";
                    System.out.println(query);
                    u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to add transaction record. Please check your columns.");
                    else System.out.println("Add transaction successful.");
                    break;
                case "5":
                    id = getTransactionId(in, con);
                    query = "delete from transaction where tid = " + id;
                    u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to delete account. Please check if the customer exists.");
                    else System.out.println("Delete account successful.");
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Unrecognized input. Try again");
                    continue;
            }
            
            System.out.print("\nDo you wish to continue (Y/N): ");
            s = in.nextLine();
            if(s.compareToIgnoreCase("Y") != 0 && s.compareToIgnoreCase("YES") != 0)
                return;
        }
    }
    
    public static void pkgManMenu(Scanner in, Connection con) throws SQLException {
        while(true) {
            System.out.println("Welcome, package manager.");
            System.out.println();
            System.out.println("Please choose your action.");
            System.out.println(" 0 - Get package list");
            System.out.println(" 1 - Get package detail");
            System.out.println(" 2 - Add a new package");
            System.out.println(" 3 - Delete an existing package");
            System.out.println("\n 9 - Previous menu");
            System.out.print("Enter your choice : ");
            String choice = in.next();
            String s = in.nextLine();
            String query;
            switch(choice) {
                case "0":
                    query = "select package.pid as package_id,weight,speed,hazardous.content as hazardous_content,international.content as intl_content,value as intl_value "
                            + "from package left outer join hazardous on package.pid = hazardous.pid left outer join international on package.pid = international.pid";
                    Value v = new Value(con, query);
                    System.out.println("A list of all packages is displayed below.");
                    v.printAll();
                    break;
                case "1":
                    int id = getPackageIdNoTnum(in,con);
                    query = "select package.pid as package_id,weight,speed,hazardous.content as hazardous_content,international.content as intl_content,value as intl_value "
                            + "from package left outer join hazardous on package.pid = hazardous.pid left outer join international on package.pid = international.pid "
                            + "where package.pid = " + id;
                    v = new Value(con, query);
                    System.out.println("The package detail is displayed below.");
                    v.printAll();
                    break;
                case "2":
                    int pid;
                    double weight, intlVal;
                    String speed, hzCont, intlCont;
                    System.out.println();
                    System.out.print("Enter the preferred id of the package: ");
                    while(!in.hasNextInt()) {
                        System.out.print("Not a valid package id. Try enter again: ");
                        s = in.next();
                    }
                    pid = in.nextInt();
                    s = in.nextLine();
                    System.out.print("Enter the weight of the package: ");
                    while(!in.hasNextDouble()) {
                        System.out.print("Not a valid weight. Try enter again: ");
                        s = in.next();
                    }
                    weight = in.nextDouble();
                    s = in.nextLine();
                    System.out.print("Enter the speed of tracking activity: ");
                    speed = in.nextLine();
                    query = "insert into package values(" + pid + "," + weight + ",'" + speed + "')";
                    Update u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to add package. Please check your columns.");
                    else System.out.println("Add package successful.");
                    System.out.print("Is the package hazardous? (Y/N): ");
                    s = in.nextLine();
                    if(s.compareToIgnoreCase("Y") == 0 || s.compareToIgnoreCase("YES") == 0) {
                        System.out.print("Enter the content of the package: ");
                        hzCont = in.nextLine();
                        query = "insert into hazardous values(" + pid + ",'" + hzCont + "')";
                        u = new Update(con, query);
                        u.execute();
                    }
                    System.out.print("Is the package international? (Y/N): ");
                    s = in.nextLine();
                    if(s.compareToIgnoreCase("Y") == 0 || s.compareToIgnoreCase("YES") == 0) {
                        System.out.print("Enter the content of the package: ");
                        intlCont = in.nextLine();
                        System.out.print("Enter the value of the content: ");
                        while(!in.hasNextDouble()) {
                            System.out.print("Not a valid dollar amount. Try enter again: ");
                            s = in.next();
                        }
                        intlVal = in.nextDouble();
                        s = in.nextLine();
                        query = "insert into international values(" + pid + ",'" + intlCont + "'," + intlVal + ")";
                        u = new Update(con, query);
                        u.execute();
                    }
                    System.out.println(query);
                    break;
                case "3":
                    id = getPackageIdNoTnum(in, con);
                    query = "delete from hazardous where pid = " + id;
                    u = new Update(con, query);
                    u.execute();
                    query = "delete from international where pid = " + id;
                    u = new Update(con, query);
                    u.execute();
                    query = "delete from package where pid = " + id;
                    u = new Update(con, query);
                    if(u.execute() == 0) System.out.println("Fail to delete tracking record. Please check if the customer exists.");
                    else System.out.println("Delete tracking record successful.");
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Unrecognized input. Try again");
                    continue;
            }
            
            System.out.print("\nDo you wish to continue (Y/N): ");
            s = in.nextLine();
            if(s.compareToIgnoreCase("Y") != 0 && s.compareToIgnoreCase("YES") != 0)
                return;
        }
    }
    
    public static int getCustomerIdName(Scanner in, Connection con, String[] name) throws SQLException {
        int id = 0, counter = 0;;
        name[0] = "";
        String s;
        System.out.println();
        while(id == 0 && name[0].equals("")) {
            if(counter++ >= 3) {
                System.out.println("You have tried 3 times already. Give us a break.");
                System.exit(0);
            }
            System.out.print("Enter userid (leave blank if unknown) : ");
            s = in.nextLine();
            if(!s.equals("")) {
                try {
                    id = Integer.parseInt(s);
                    String getName = "select name from customer where cid = " + id;
                    name[0] = new Value(con, getName).getData().get(0)[0];
                }
                catch (NumberFormatException e) {System.out.println("Not a valid user id.");}
                catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); id = 0; continue;}
            }
            else {
                System.out.print("Enter customer's name: ");
                name[0] = in.nextLine();
                String getId = "select cid from customer where name = '" + name[0] + "'";
                try {id = Integer.parseInt(new Value(con, getId).getData().get(0)[0]);}
                catch(IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); name = null; continue;}
            }
        }
        return id; 
    }
    
    public static int getCustomerId(Scanner in, Connection con) throws SQLException {
        int id = 0, counter = 0;
        String name = null;
        String s;
        System.out.println();
        while(id == 0 && name == null) {
            if(counter++ >= 3) {
                System.out.println("You have tried 3 times already. Give us a break.");
                System.exit(0);
            }
            System.out.print("Enter userid (leave blank if unknown) : ");
            s = in.nextLine();
            if(!s.equals("")) {
                try {
                    id = Integer.parseInt(s);
                    String getName = "select name from customer where cid = " + id;
                    name = new Value(con, getName).getData().get(0)[0];
                }
                catch (NumberFormatException e) {System.out.println("Not a valid user id.");}
                catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); id = 0; continue;}
            }
            else {
                System.out.print("Enter customer's name: ");
                name = in.nextLine();
                String getId = "select cid from customer where name = '" + name + "'";
                try {id = Integer.parseInt(new Value(con, getId).getData().get(0)[0]);}
                catch(IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); name = null; continue;}
            }
        }
        return id;
    }
    
    public static int getPackageId(Scanner in, Connection con) throws SQLException {
        int id = 0, counter = 0;
        long tnum = 0;
        String s;
        System.out.println();
        while(id == 0 && tnum == 0) {
            if(counter++ >= 3) {
                System.out.println("You have tried 3 times already. Give us a break.");
                System.exit(0);
            }
            System.out.print("Enter package id (leave blank for tracking number) : ");
            s = in.nextLine();
            if(!s.equals("")) {
                try {
                    id = Integer.parseInt(s);
                    String getTnum = "select tnum from tracking where pid = " + id;
                    tnum = Long.parseLong(new Value(con, getTnum).getData().get(0)[0]);
                }
                catch (NumberFormatException e) {System.out.println("Not a valid package id.");}
                catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); id = 0; continue;}
            }
            else {
                System.out.print("Enter tracking number: ");
                s = in.nextLine();
                try {
                    tnum = Long.parseLong(s);
                    String getId = "select pid from tracking where tnum = " + tnum;
                    id = Integer.parseInt(new Value(con, getId).getData().get(0)[0]);
                }
                catch (NumberFormatException e) {System.out.println("Not a valid tracking number.");}
                catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); tnum = 0; continue;}
            }
        }
        return id;
    }
    
    public static int getPackageIdNoTnum(Scanner in, Connection con) throws SQLException {
        int id = 0, counter = 0, pid = 0;
        String s;
        System.out.println();
        while(id == 0) {
            if(counter++ >= 3) {
                System.out.println("You have tried 3 times already. Give us a break.");
                System.exit(0);
            }
            System.out.print("Enter package id: ");
            s = in.nextLine();
            try {
                id = Integer.parseInt(s);
                String getTnum = "select pid from package where pid = " + id;
                pid = Integer.parseInt(new Value(con, getTnum).getData().get(0)[0]);
            }
            catch (NumberFormatException e) {System.out.println("Not a valid package id.");}
            catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); id = 0; continue;}
        }
        return id;
    }
    
    public static int getAccountId(Scanner in, Connection con) throws SQLException {
        int id = 0, counter = 0, tid = 0;
        String s;
        System.out.println();
        while(id == 0 && tid == 0) {
            if(counter++ >= 3) {
                System.out.println("You have tried 3 times already. Give us a break.");
                System.exit(0);
            }
            System.out.print("Enter account id (leave blank for transaction id) : ");
            s = in.nextLine();
            if(!s.equals("")) {
                try {
                    id = Integer.parseInt(s);
                    String getTid = "select tid from transaction where aid = " + id;
                    tid = Integer.parseInt(new Value(con, getTid).getData().get(0)[0]);
                }
                catch (NumberFormatException e) {System.out.println("Not a valid account id.");}
                catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); id = 0; continue;}
            }
            else {
                System.out.print("Enter transaction id: ");
                s = in.nextLine();
                try {
                    tid = Integer.parseInt(s);
                    String getId = "select aid from transaction where tid = " + tid;
                    id = Integer.parseInt(new Value(con, getId).getData().get(0)[0]);
                }
                catch (NumberFormatException e) {System.out.println("Not a valid tracking number.");}
                catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); tid = 0; continue;}
            }
        }
        return id;
    }
    
    public static int getTransactionId(Scanner in, Connection con) throws SQLException {
        int id = 0, counter = 0, tid = 0;
        String s;
        System.out.println();
        while(id == 0) {
            if(counter++ >= 3) {
                System.out.println("You have tried 3 times already. Give us a break.");
                System.exit(0);
            }
            System.out.print("Enter transaction id: ");
            s = in.nextLine();
            try {
                id = Integer.parseInt(s);
                String getTid = "select tid from transaction where tid = " + id;
                tid = Integer.parseInt(new Value(con, getTid).getData().get(0)[0]);
            }
            catch (NumberFormatException e) {System.out.println("Not a valid transaction id.");}
            catch (IndexOutOfBoundsException e) {System.out.println("Cannot find record. Try enter again."); id = 0; continue;}
        }
        return id;
    }
}
