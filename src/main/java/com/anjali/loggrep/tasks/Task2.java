package com.anjali.loggrep.tasks;

import com.anjali.loggrep.utils.DateUtils;
import com.anjali.loggrep.pojo.LogEntry;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Split each log line into two parts:
 * (a) time/date and (b) rest.
 * Search query now has two parameters: date and time range, and pattern to
 * match over (b)
 */
public class Task2 {

    private Date startDate;
    private Date endDate;
    private String regex;
    private String directoryPath = "./logs/";

    public void matchFolder() {
        Pattern pattern = Pattern.compile(regex);
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    matchFile(pattern, file);
                }
            }
        }
    }

    private void matchFile(Pattern p, File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (new BufferedInputStream(new FileInputStream(file))));

            String line;
            while ((line = reader.readLine()) != null) {
                try{
                    LogEntry entry = new LogEntry(line);
                    if(entry.findPattern(p, startDate, endDate)) {
                        System.out.println(line);
                    }
                } catch(ParseException e) {
                    System.out.println("Invalid date entry in line: " + line);
                }

            }
        } catch(IOException e) {
            System.out.println("Invalid File name or Error while reading file");
            e.printStackTrace();;
        }
    }

    public static void main(String[] args) {
        if(args.length < 4) {
            usage();
            System.exit(1);
        }

        Task2 o = new Task2();
        try {
            o.startDate = DateUtils.parseStringToDate(args[1]);
            o.endDate = DateUtils.parseStringToDate(args[2]);
            o.regex = args[3];
            if (args.length > 4) {
                o.directoryPath = args[4];
            }
            o.matchFolder();
        } catch (ParseException e) {
            System.out.println("Date input invalid. Provide date in //[01/Jul/1995:00:02:20-0400//] format only");
        }
    }

    private static void usage() {
        System.out.println("Usage: Task2 [--startTS <startTS>]" +
                "[--endTS <endTS>]" +
                "[--pattern <pattern>]" +
                "[--directoryPath <directoryPath>]");
    }
}
