/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Package {
    public static final int COUNT = Customer.COUNT, IDDIGIT = Customer.IDDIGIT;
    public static void packageGen() {
        int id = (int)Math.pow(10,IDDIGIT - 1);
        for(int i = 0; i < COUNT; i++) {
            id ++;
            double weight = wghtGen();
            String speed = speedGen();
            System.out.printf("insert into package values(%d,%.2f,'%s');\n",id,weight,speed);
        }
    }
    
    private static String speedGen() {
        int rand = (int)(Math.random() * 4);
        switch(rand) {
            case 0:
                return "Next Day Air";
            case 1:
                return "2nd Day Air";
            case 2:
                return "3rd Day Air";
            case 3:
                return "Ground";
            default:
                return null;
        }
    }
    
    private static double wghtGen() {
        double rand = Math.random() * (100) + 1;
        return rand;
    }
}
