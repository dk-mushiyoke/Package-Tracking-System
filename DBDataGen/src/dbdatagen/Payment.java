/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Payment {
    public static final int COUNT = Package.COUNT, IDDIGIT = Package.IDDIGIT;
    public static void paymentGen() {
        int id = (int)Math.pow(10,IDDIGIT - 1);
        for(int i = 0; i < COUNT; i++) {
            id ++;
            int transId = transIdGen();
            System.out.printf("insert into payment values(%d,%d);\n",id,transId);
        }
    }
    
    private static int transIdGen() {
        int rand = (int)(Math.random() * (Transaction.COUNT) + 1);
        return (int)Math.pow(10,Transaction.IDDIGIT - 1) + rand;
    }
}
