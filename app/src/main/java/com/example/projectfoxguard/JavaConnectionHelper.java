package com.example.projectfoxguard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaConnectionHelper {
    //private val database = "FoxGuard"
    //private val uname = "sa"
    //private val pass = "zc0w8j2m"

    //private val ip = "192.168.0.13:1433"

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    static String connectionUrl =
                "jdbc:sqlserver://LAPTOP-66PNGFC3:1433;"
                        + "encrypt=true;"
                        + "database=FoxGuard;"
                        + "user=sa;" //@LAPTOP-66PNGFC3
                        + "password=zc0w8j2m;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";



}
