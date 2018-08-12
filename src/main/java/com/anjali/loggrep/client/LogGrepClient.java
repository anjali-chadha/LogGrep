package com.anjali.loggrep.client;

import com.anjali.loggrep.remoteinterface.LogGrep;
import com.anjali.loggrep.utils.DateUtils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;

public class LogGrepClient {

    public static void main(String args[]) throws RemoteException
    {
        if(args.length < 4) {
            usage();
            System.exit(1);
        }
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            LogGrep obj = (LogGrep) registry.lookup("LogGrepServer");
            obj.findPattern(args[3], DateUtils.parseStringToDate(args[1]), DateUtils.parseStringToDate(args[2]));
            System.out.println("Printing 1st page output: ");
            System.out.println(obj.getPage(1));
        }
        catch (ParseException e) {
            System.out.println("Date input invalid. Provide date in //[01/Jul/1995:00:02:20-0400//] format only");
        }
        catch (Exception e)
        {
            System.out.println("LogGrepServer exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.out.println("Usage: LogGrepClient [--startTS <startTS>]" +
                "[--endTS <endTS>]" +
                "[--pattern <pattern>]");
    }
}
