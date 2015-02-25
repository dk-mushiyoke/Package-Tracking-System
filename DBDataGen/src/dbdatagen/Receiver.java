/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Receiver {
    public static final int COUNT = Package.COUNT, IDDIGIT = Package.IDDIGIT;
    public static void receiverGen() {
        int id = (int)Math.pow(10,IDDIGIT - 1);
        BufferedReader instreet, incity, incountry;
        ArrayList<String> streetList = new ArrayList(),
                          cityList = new ArrayList(),
                          countryList = new ArrayList();
        String[] stateList = {"AL","AK","AZ","AR","CA","CO","CT","DE","DC","FL",
            "GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI",
            "MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH",
            "OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV",
            "WI","WY"};
        try{
            instreet = new BufferedReader(new FileReader("streetName.txt"));
            incity = new BufferedReader(new FileReader("cityName.txt"));
            incountry = new BufferedReader(new FileReader("countryName.txt"));
            String line;
            while((line = instreet.readLine()) != null) {streetList.add(line);}
            while((line = incity.readLine()) != null) {cityList.add(line);}
            while((line = incountry.readLine()) != null) {countryList.add(line);}
            instreet.close();
            incity.close();
            incountry.close();
        } catch(Exception e){e.printStackTrace();}
        for(int i = 0; i < COUNT; i++) {
            id ++;
            int custId = custIdGen();
            String street = streetGen(streetList);
            String city = cityGen(cityList);
            String state = stateGen(stateList);
            int zip = zipGen();
            String country = countryGen(countryList);
            String exp_dtime = timeGen();
            System.out.printf("insert into receiver values(%d,%d,'%s','%s','%s',%d,'%s',timestamp '%s');\n",id,custId,street,city,state,zip,country,exp_dtime);
        }
    }
    
    private static int custIdGen() {
        int rand = (int)(Math.random() * (Customer.COUNT) + 1);
        return (int)Math.pow(10,Customer.IDDIGIT - 1) + rand;
    }
    
    private static String streetGen(ArrayList list) {
        int number = (int)(Math.random() * 9999 + 1);
        String name = (String)list.get((int)(Math.random() * list.size()));
        return "" + number + " " + name;
    }

    private static String cityGen(ArrayList list) {
        String str = (String)list.get((int)(Math.random() * list.size()));
        return str;
    }

    private static String stateGen(String[] list) {
        String str = list[(int)(Math.random() * list.length)];
        return str;
    }

    private static int zipGen() {
        int number = (int)(Math.random() * 99999 + 1);
        while(number < 10000) number *= 10;
        return number;
    }
    
    private static String countryGen(ArrayList list) {
        int rand = (int)(Math.random() * 750);
        if(rand >= list.size()) rand = list.size() - 1;
        String str = (String)list.get(rand);
        return str;
    }
    
    private static String timeGen() {
        long start = 1129114800L, end = 1332306630L;
        long rand = start + (long)(Math.random() * (end - start));
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rand*1000);
        return date;
    }
}
