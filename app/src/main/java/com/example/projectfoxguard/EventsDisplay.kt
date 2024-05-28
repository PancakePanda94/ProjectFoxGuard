package com.example.projectfoxguard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.ResultSet
import java.util.concurrent.Executors

class EventsDisplay : AppCompatActivity() {
    private var ConnectionHelper = ConnectionHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private val dataList = mutableListOf<DataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_events_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with empty dataList and set it to RecyclerView
        adapter = ItemAdapter(dataList)
        recyclerView.adapter = adapter

        // Fetch data from SQL server
        fetchDataFromSqlServer()

    }


    private fun fetchDataFromSqlServer() {
        // Use a background thread to fetch data
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val connection: Connection? = ConnectionHelper.DBConnection()
            if (connection != null) {
                try {
                    val query =
                        "SELECT eventId, eventName, eventType, eventLocation, eventDate, eventDescription FROM EVENTSINFO WHERE eventLocation is not null" /*+
                                " WHERE eventDate >= CAST(GETDATE() AS DATE)" */
                    val events: PreparedStatement = connection.prepareStatement(query)
                    val resultSet: ResultSet = events.executeQuery()
                    val fetchedData = mutableListOf<DataModel>()

                    while (resultSet.next()) {
                        val eventId = resultSet.getString("eventId")
                        val eventName = resultSet.getString("eventName")
                        val eventType = resultSet.getString("eventType")
                        val eventLocation = resultSet.getString("eventLocation")
                        val eventDate = resultSet.getString("eventDate")
                        val eventDescription = resultSet.getString("eventDescription")

                        fetchedData.add(
                            DataModel(
                                eventId,
                                eventName,
                                eventType,
                                eventLocation,
                                eventDate,
                                eventDescription
                            )
                        )
                    }

                    handler.post {
                        dataList.clear()
                        dataList.addAll(fetchedData)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this@EventsDisplay, "EVENTOS FOUND", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (ex: SQLException) {
                    handler.post {
                        Toast.makeText(
                            this@EventsDisplay,
                            "EVENTOS NO ENCONTRADO",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.e("Error: ", ex.message.toString())
                } finally {
                    try {
                        connection.close()
                    } catch (ex: SQLException) {
                        Log.e("Error: ", ex.message.toString())
                    }
                }
            } else {
                handler.post {
                    Toast.makeText(
                        this@EventsDisplay,
                        "Database connection not available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}