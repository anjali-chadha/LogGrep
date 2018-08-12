package com.anjali.loggrep.remoteinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface LogGrep extends Remote {

    void findPattern(String pattern, Date startDate, Date endDate)throws RemoteException;
    List<String> nextPage() throws RemoteException;
    List<String> nextPage(int singlePageSize) throws RemoteException;
    List<String> getPage(int pageNo) throws RemoteException;
}
