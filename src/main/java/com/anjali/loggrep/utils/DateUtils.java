package com.anjali.loggrep.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat format1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ssZ");
    private DateUtils() {

    }

    public static Date parseStringToDate(String s) throws ParseException {
        return format1.parse(s);
    }

    public static boolean isWithinRange(Date startDate, Date endDate, Date date) {
        if(startDate == null || endDate == null || date == null) {return false;}
        return !(date.before(startDate) || date.after(endDate));
    }
}
