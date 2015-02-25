/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class RegPrice {
    public static final int COUNT = Package.COUNT, IDDIGIT = Package.IDDIGIT;
    public static void regpriceGen() {
        int id = (int)Math.pow(10,IDDIGIT - 1);
        String[] descList = {"Type  1","Type  2","Type  3","Type  4",
            "Type  5","Type  6","Type  7","Type  8"};
        double[] priceList = {67.95, 9, 64.14, 53.07, 62.57, 66.17, 94.39, 36.64};
        for(int i = 0; i < COUNT; i++) {
            id ++;
            int rand = (int)(Math.random() * priceList.length);
            String desc = descList[rand];
            double price = priceList[rand];
            System.out.printf("insert into regular_price values(%d,'%s',%.2f);\n",id,desc,price);
        }
    }
}
