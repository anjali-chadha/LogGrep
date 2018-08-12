# LogGrep
A client-server version of grep for logs which  allows developers to quickly sift through a large collection of webserver logs

## Functionalities 
* Match regular expressions over a directory of files
* Split each log line into two parts: (a) time/date and (b) rest. Search query now has two parameters: date and time range, and pattern to match over (b).
* A server using RPC query service. That is, the client sub- mits a query by invoking a remote procedure call.
* Supports Pagination–return query results in pages. Show number of lines that match

## Execution Steps:
1. Start Server
   ```
   java -jar loggrepserver.jar <log directory>
   ```
2. Start Client
   ```
   java -jar client.jar LogGrepClient <startdate> <enddate> <regexFormat>
   ```
