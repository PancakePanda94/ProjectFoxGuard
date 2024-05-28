package com.example.projectfoxguard
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

public class ConnectionHelper {
       private val database = "FoxGuard"
        private val uname = "sa"
        private val pass = "zc0w8j2m"
        private val ipcetys = "10.4.76.195"
        private val ip = "192.168.0.11:1433"

        fun DBConnection(): Connection?{

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            var conn : Connection? = null
            val connString : String

            try{
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
                connString= "jdbc:jtds:sqlserver://$ip;databaseName=$database;user=$uname;password=$pass"
                conn = DriverManager.getConnection(connString)

            }catch (ex: SQLException){
                Log.e("Error: ", ex.message.toString())
            }
            return conn
        }
    }