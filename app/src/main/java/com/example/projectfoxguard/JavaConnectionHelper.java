package com.example.projectfoxguard;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaConnectionHelper {
    private String database = "FoxGuard";
    private String uname = "sa";
    private String pass = "zc0w8j2m";

    private  String ipcetys = "10.4.76.195:1433";
    private String ip = "192.168.0.5:1433";

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    String classes= "net.sourceforge.jtds.jdbc.Driver";

    public Connection conn() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try {
            Class.forName(classes);
            String connString= "jdbc:jtds:sqlserver://"+ip+";databaseName="+database;
            DriverManager.setLoginTimeout(10);
            conn = DriverManager.getConnection(connString,uname,pass);
        }
        catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
        return conn;
    }
   /* static String connectionUrl =
                "jdbc:sqlserver://LAPTOP-66PNGFC3;"
                        + "encrypt=true;"
                        + "database=FoxGuard;"
                        + "user=sa;" //@LAPTOP-66PNGFC3
                        + "password=zc0w8j2m;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
*/


}
