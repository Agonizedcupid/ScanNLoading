package com.aariyan.scannloading.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Constant {

    public static String IP_URL = "IP_URL";
    public static String IP_MODE_KEY = "com.aariyan.scannloading.BASE_URL";

    public static String DEFAULT = "N/A";

    public static int userId = 1;

    public static String[] types = {"UPDATE"};
    public static int[] flag = {0, 1, 2};
    /*
    0 == Black zone
    1 == Green Zone
    2 == Red Zone
     */

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
