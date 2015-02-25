/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdatagen;

/**
 * @author Di Kong <dik214@lehigh.edu>
 * 
 */
public class Tracking {
    public static final int COUNT = Package.COUNT, NUMDIGIT = 12;
    public static void trackingGen() {
        for(int i = 0; i < COUNT; i++) {
            long tcknum = numGen();
            int pkgId = pkgIdGen();
            String timestamp = timeGen();
            String activity = actGen();
            int locid = locIdGen();
            System.out.printf("insert into tracking values(%d,%d,timestamp '%s','%s',%d);\n",tcknum,pkgId,timestamp,activity,locid);
            timestamp = timeGen();
            activity = actGen();
            locid = locIdGen();
            System.out.printf("insert into tracking values(%d,%d,timestamp '%s','%s',%d);\n",tcknum,pkgId,timestamp,activity,locid);
            timestamp = timeGen();
            activity = actGen();
            locid = locIdGen();
            System.out.printf("insert into tracking values(%d,%d,timestamp '%s','%s',%d);\n",tcknum,pkgId,timestamp,activity,locid);
        }
    }
    
    private static long numGen() {
        long rand = (long)(Math.random() * 999999999999L + 1);
        return rand;
    }
    
    private static int pkgIdGen() {
        int rand = (int)(Math.random() * (Package.COUNT) + 1);
        return (int)Math.pow(10,Package.IDDIGIT - 1) + rand;
    }
    
    private static String timeGen() {
        long start = 1129114800L, end = 1332306630L;
        long rand = start + (long)(Math.random() * (end - start));
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rand*1000);
        return date;
    }
    
    private static String actGen() {
        int rand = (int)(Math.random() * 5);
        switch(rand) {
            case 0:
                return "Arrival Scan";
            case 1:
                return "Departure Scan";
            case 2:
                return "In Transit";
            case 3:
                return "Delivered";
            case 4:
                return "Out for delivery";
            default:
                return null;
        }
    }
    
    private static int locIdGen() {
        int rand = (int)(Math.random() * (Location.COUNT) + 1);
        return (int)Math.pow(10,Location.IDDIGIT - 1) + rand;
    }
    
}
