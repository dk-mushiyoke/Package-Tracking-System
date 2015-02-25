/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Transaction {
    public static final int COUNT = Account.COUNT * 2, IDDIGIT = 8;
    public static void transGen() {
        int id = (int)Math.pow(10,IDDIGIT - 1);
        for(int i = 0; i < COUNT; i++) {
            id ++;
            int acctId = acctIdGen();
            String timestamp = timeGen();
            double amount = amntGen();
            System.out.printf("insert into transaction values(%d,%d,timestamp '%s',%.2f);\n",id,acctId,timestamp,amount);
        }
    }
    
    private static String timeGen() {
        long start = 1129114800L, end = 1332306630L;
        long rand = start + (long)(Math.random() * (end - start));
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rand*1000);
        return date;
    }
    
    private static int acctIdGen() {
        int rand = (int)(Math.random() * (Account.COUNT) + 1);
        return (int)Math.pow(10,Account.IDDIGIT - 1) + rand;
    }
    
    private static double amntGen() {
        double amount = Math.random() * 99 + 1;
        return amount;
    }
}
