/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Service {
    public static final int COUNT = 8;
    public static void serviceGen() {
        for(int i = 0; i < COUNT; i++) {
            String desc = "Type  " + (i+1);
            double price = priceGen();
            System.out.printf("insert into service values('%s',%.2f);\n",desc,price);
        }
    }
    
    private static double priceGen() {
        double rand = Math.random() * (100) + 1;
        return rand;
    }
}
