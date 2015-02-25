/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Account {
    public static final int COUNT = (int)(Customer.COUNT * 1.5), IDDIGIT = 6;
    public static void accountGen() {
        int id = (int)Math.pow(10,IDDIGIT - 1);
        for(int i = 0; i < COUNT; i++) {
            id ++;
            String type = typeGen();
            int custId = custIdGen();
            System.out.printf("insert into account values(%d,%d,'%s');\n",id,custId,type);
        }
    }
    
    private static String typeGen() {
        int rand = (int)(Math.random() * 2);
        return rand == 0 ? "credit":"debit";
    }
    
    private static int custIdGen() {
        int rand = (int)(Math.random() * (Customer.COUNT) + 1);
        return (int)Math.pow(10,Customer.IDDIGIT - 1) + rand;
    }
}
