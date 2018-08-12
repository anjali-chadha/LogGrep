package com.anjali.loggrep.pojo;

import com.anjali.loggrep.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private Date date;
    private String remaining;

    public LogEntry(String log) throws ParseException {
        String[] arr = log.split(" - - \\[");
        if(arr.length == 2) {
            String[] arr2 = arr[1].split("\\]");
            if(arr2.length == 2) {
                this.date = DateUtils.parseStringToDate(arr2[0].replace(" ", ""));
                this.remaining = arr[0] + arr2[1];
            }
        }
    }

    public LogEntry(String date, String remaining) throws ParseException {
        this.date = DateUtils.parseStringToDate(date);
        this.remaining = remaining;
    }

    private boolean findPattern(Pattern p) {
        Matcher m = p.matcher(remaining);
        return m.find();
    }

    public boolean findPattern(Pattern p, Date startDate, Date endDate) {
        return DateUtils.isWithinRange(startDate, endDate, date) && findPattern(p);
    }



}