package com.anjali.loggrep.server;

import com.anjali.loggrep.pojo.LogEntry;
import com.anjali.loggrep.pojo.Pagination;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class LogGrepFolder {
    private Date startDate;
    private Date endDate;
    private String regex;
    private String directoryPath = "./logs/";
    private List<String> result;
    private Pagination pagination;

    public void findPattern(String pattern, Date startDate, Date endDate, String directorPath) {
        System.out.println("Query received for pattern " + pattern + " and date range: " + startDate + "-" + endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.directoryPath = directorPath;
        regex = pattern;
        result = new ArrayList<>();
        pagination = null;
        matchFolder();
    }

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

    public Pagination getPagination(int singlePageSize) {
        if(pagination == null) {
            pagination = new Pagination(result, singlePageSize);
        }
        return pagination;
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
                        result.add(line);
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
}
