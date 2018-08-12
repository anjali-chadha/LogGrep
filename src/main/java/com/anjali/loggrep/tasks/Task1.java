package com.anjali.loggrep.tasks;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Match regular expressions over a directory of files.
 */
public class Task1 {

    public void matchFolder(String regex, String dir) {
        Pattern pattern = Pattern.compile(regex);
        File folder = new File(dir);
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
                Matcher m = p.matcher(line);
                if(m.find()) {
                    System.out.println(line);
                }
            }
        } catch(IOException e) {
            System.out.println("Invalid File name or Error while reading file");
            e.printStackTrace();;
        }
    }

    public static void main(String[] args) {
        Task1 o = new Task1();
        o.matchFolder("win[vn]", "./logs/");
    }

    private void usage() {

    }
}
