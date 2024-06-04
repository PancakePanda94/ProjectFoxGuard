package com.example.projectfoxguard

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfoxguard.databinding.ActivityMainBinding
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class eventSelector : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var ConnectionHelper = ConnectionHelper()
    private lateinit var spinnerEvents: Spinner
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        initViews()
        setContentView(R.layout.activity_event_selector)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        spinnerEvents = findViewById(R.id.EventSpinner)
        getActiveEvents()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        //val selectedEventId = spinnerEvents.selectedItemId
        binding.fab.setOnClickListener{
            val intent = Intent(this, qrGenerator::class.java)
            //int StudentId= Integer.parseInt(getIntent().getStringExtra("StudentId"));
            intent.putExtra("Matricula",  spinnerEvents.selectedItemId)
            startActivity(intent)
        }
    }
    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun toQR(view: View?) {
        val id = spinnerEvents.selectedItemId
        val intent = Intent(this, qrGenerator::class.java)
        //int StudentId= Integer.parseInt(getIntent().getStringExtra("StudentId"));
        intent.putExtra("Matricula", id.toString() )
        startActivity(intent)
    }
    private fun getActiveEvents() {
        // binding.textResult.text = string
        // val scannedData: String = string
        val connection = ConnectionHelper.DBConnection()
        if(connection!=null) {
            try {
                val events: PreparedStatement = connection.prepareStatement("SELECT eventName FROM EVENTSINFO")
                val resultSet: ResultSet = events.executeQuery()

                // ArrayList to store the event names
                val eventNames = mutableListOf<String>()

                while(resultSet.next()) {
                    // Extract eventName from the result set and add it to the list
                    val eventName = resultSet.getString("eventName")
                    eventNames.add(eventName)
                }
                val eventNamesArray: Array<String> = eventNames.toTypedArray()
                Toast.makeText(this, "Eventos encontrados", Toast.LENGTH_SHORT).show()
                //val events: PreparedStatement = ConnectionHelper.DBConnection()
                //   ?.prepareStatement("SELECT eventName FROM ATTENDEES")!!

                //events.executeQuery()
                //Toast.makeText(this, "Eventos encontrados", Toast.LENGTH_SHORT).show()
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventNamesArray)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEvents.adapter = adapter
                // val asistencia: PreparedStatement = ConnectionHelper.DBConnection()?.prepareStatement("INSERT INTO ATTENDANCE VALUES($intent.Mat.getString(),eventId,durrentDate)")
            } catch (ex: SQLException) {
                Toast.makeText(this, "Eventos NO ENCONTRADOS", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", ex.message.toString())
            }
        }else{
            Toast.makeText(this, "Database connection not available", Toast.LENGTH_SHORT).show()
        }


    }
}