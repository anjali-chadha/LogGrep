package com.anjali.loggrep.server;

import com.anjali.loggrep.remoteinterface.LogGrep;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

/**
 * Main Server class for finding patterns in logs
 */
public class LogGrepServer extends UnicastRemoteObject implements LogGrep {
    private int currentPage;
    private LogGrepFolder grepFolder;
    private int singlePageSize = 10;
    private String logsPath;

    public LogGrepServer() throws RemoteException {
        grepFolder = new com.anjali.loggrep.server.LogGrepFolder();
    }

    @Override
    public void findPattern(String pattern, Date startDate, Date endDate) {
        currentPage = 0;
        grepFolder.findPattern(pattern, startDate, endDate, logsPath);
    }

    @Override
    public List<String> nextPage() {
        return nextPage(singlePageSize);
    }

    @Override
    public List<String> nextPage(int singlePageSize) {
        return grepFolder.getPagination(singlePageSize).getPage(currentPage++);
    }

    @Override
    public List<String> getPage(int pageNo) {
        return grepFolder.getPagination(singlePageSize).getPage(pageNo - 1);
    }

    public static void main(String[] args) {
        try
        {
            if(args.length < 1) {
                usage();
            }

            LogGrepServer obj = new LogGrepServer();
            // Bind this object instance to the name "LogGrepServer"
            Registry registro = LocateRegistry.createRegistry(1099);
            obj.logsPath = args[0];
            System.out.println("Starting server");
            registro.bind("LogGrepServer", obj);
            System.setProperty("java.security.policy", "file:/./conf/server.policy");
        }
        catch (Exception e)
        {
            System.out.println("LogGrepServer err: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.out.println("Usage: LogGrepServer [--logsPath <logPath>]");
    }
}
