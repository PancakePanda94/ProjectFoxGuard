package com.example.projectfoxguard

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfoxguard.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
// %use dataframe
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import java.time.format.DateTimeFormatter
class StaffScan : AppCompatActivity() {


    private var ConnectionHelper = ConnectionHelper()
    private lateinit var spinnerEvents: Spinner
    //val dbConfig = DatabaseConfiguration(URL, USER_NAME, PASSWORD)
    @RequiresApi(Build.VERSION_CODES.O)
    private val requestPermissionLauncher=
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
            if(isGranted){
                showCamera()
            }else{

            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private val scanLauncher =
        registerForActivityResult(ScanContract()){
            result: ScanIntentResult ->
            run {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    setResult(result.contents)
                }
            }
        }

    private lateinit var binding: ActivityMainBinding

    /* private fun setResult(string: String){
        binding.textResult.text = string
        val scannedData: String = string
        val connection = ConnectionHelper.DBConnection()
        if(connection!=null) {
            try {
                val matricula: PreparedStatement = ConnectionHelper.DBConnection()
                    ?.prepareStatement("SELECT top 1 * FROM ATTENDEES WHERE attendeeId =$scannedData ")!!

                matricula.executeQuery()
                Toast.makeText(this, "Estudiante ENCONTRADO", Toast.LENGTH_SHORT).show()

               // val asistencia: PreparedStatement = ConnectionHelper.DBConnection()?.prepareStatement("INSERT INTO ATTENDANCE VALUES($intent.Mat.getString(),eventId,durrentDate)")
            } catch (ex: SQLException) {
                Toast.makeText(this, "Estudiante NO ENCONTRADO", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", ex.message.toString())
            }
        }else{
            Toast.makeText(this, "Database connection not available", Toast.LENGTH_SHORT).show()
        }
    }

     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        initViews()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerEvents = findViewById(R.id.EventSpinner)
        getActiveEvents()

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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.fab.setOnClickListener{
            checkPermissionCamera(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkPermissionCamera(context: Context) {
        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED) {
            showCamera()
        }
        else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context,"Acceso a Camara requerido", Toast.LENGTH_SHORT).show()
        }
        else{

            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun setResult(string: String) {
        binding.textResult.text = string
        val scannedData: String = string
        val currentTime = LocalDateTime.now() // Get the current date and time
        //val selectedEvent = spinnerEvents.selectedItem.toString() // Get the selected item from the spinner
        val selectedEventId = spinnerEvents.selectedItemId // Get the selected item's ID from the spinner
        val connection = ConnectionHelper.DBConnection()
        if (connection != null) {
            try {
                val formattedDateTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                // Assuming your SQL query to insert data into the database looks like this
                val insertQuery = "INSERT INTO ATTENDANCE (attendeeId, eventId, entryTime) VALUES (?, ?, ?)"
                val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

                // Assuming attendeeId is a string, you might need to adjust the data type accordingly
                preparedStatement.setString(1, scannedData)
                preparedStatement.setLong(2, selectedEventId)
                preparedStatement.setString(3,formattedDateTime)

                val rowsAffected = preparedStatement.executeUpdate()
                Log.e("Error: ", "Here")
                if (rowsAffected > 0) {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: SQLException) {
                Toast.makeText(this, "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", ex.message.toString())
            }
        } else {
            Toast.makeText(this, "Database connection not available", Toast.LENGTH_SHORT).show()
        }
    }
}