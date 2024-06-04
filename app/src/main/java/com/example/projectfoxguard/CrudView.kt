package com.example.projectfoxguard

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.sourceforge.jtds.jdbc.DateTime
import java.sql.PreparedStatement
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class CrudView : AppCompatActivity() {
    //Variables for Inputs

    lateinit var boxName:EditText
    lateinit var boxType: EditText

    lateinit var boxLocation: EditText
    lateinit var boxDescription:EditText
    private lateinit var editTextEventDate: Button
    private lateinit var editTextEventTime: Button
    //Variables for buttons
    lateinit var Addbtn: Button
    private var eventDateTime: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crud_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Variables for user input  (EventId, eventNames, eventType, eventDate, EventLocation, eventDescription)
        //boxId = findViewById(R.id.editTextID)
        editTextEventDate = findViewById(R.id.btnDatePicker)
        editTextEventTime = findViewById(R.id.btnTimePicker)
        boxName = findViewById(R.id.editTextName)
        boxType = findViewById(R.id.editTextType)
       // boxDate = findViewById(R.id.editTextDate)
        boxLocation = findViewById(R.id.editTextLocation)
        boxDescription = findViewById(R.id.editTextDesc)

        //Variables for Buttons (Add, Delete, Update, and Search)
        Addbtn = findViewById(R.id.btnAdd)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        val btnTimePicker: Button = findViewById(R.id.btnTimePicker)

        btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
        btnTimePicker.setOnClickListener {
            showTimePickerDialog()
        }
        //Add a New Event to the Data Base
        Addbtn.setOnClickListener{
            val connection = ConnectionHelper()
            if(connection!=null) {
                try {
                    //SQL validation
                    val AddEvent: PreparedStatement = connection.DBConnection()?.prepareStatement("Insert into EVENTSINFO ( EventName, EventType, EventLocation, EventDate, EventDescription) VALUES (?, ?, ?, ?, ?)")!!
                    val eventName = boxName.text.toString()
                    val eventType = boxType.text.toString()
                    val eventLocation = boxLocation.text.toString()

                    val inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val sqlFormattedDate = sdf.format(eventDateTime.time)
                    // Parse the input date string to a LocalDate object
                    //val parsedDate = LocalDate.parse(eventDate, inputFormatter)

                    // Convert the LocalDate to a LocalDateTime
                    //val parsedDateTime = parsedDate.atStartOfDay()

                    // Format the LocalDateTime object to the desired output format
                    //val sqlFormattedDate = parsedDateTime.format(outputFormatter)

                    val eventDescription = boxDescription.text.toString()

                    AddEvent.setString(1, eventName)
                    AddEvent.setString(2, eventType)
                    AddEvent.setString(3, eventLocation)
                    AddEvent.setString(4, sqlFormattedDate)
                    AddEvent.setString(5, eventDescription)
                    //Content to be added
                    //AddEvent.setString(2, boxName.text.toString())
                    //AddEvent.setString(3, boxType.text.toString())
                    //AddEvent.setString(4, boxDate.text.toString())
                    //AddEvent.setString(5, boxLocation.text.toString())
                    //AddEvent.setString(6, boxDescription.text.toString())
                    AddEvent.executeUpdate()

                    Toast.makeText(this, "Event Has been added successfully", Toast.LENGTH_SHORT)
                        .show()
                } catch (ex: SQLException) {
                    Toast.makeText(this, "Insertion Error : ${ex.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun showDatePickerDialog() {
        val year = eventDateTime.get(Calendar.YEAR)
        val month = eventDateTime.get(Calendar.MONTH)
        val day = eventDateTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            eventDateTime.set(Calendar.YEAR, selectedYear)
            eventDateTime.set(Calendar.MONTH, selectedMonth)
            eventDateTime.set(Calendar.DAY_OF_MONTH, selectedDay)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            editTextEventDate.setText(sdf.format(eventDateTime.time))
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val hour = eventDateTime.get(Calendar.HOUR_OF_DAY)
        val minute = eventDateTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            eventDateTime.set(Calendar.HOUR_OF_DAY, selectedHour)
            eventDateTime.set(Calendar.MINUTE, selectedMinute)
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            //editTextEventDate.append(" " + sdf.format(eventDateTime.time))
            editTextEventTime.setText(sdf.format(eventDateTime.time))
        }, hour, minute, true)

        timePickerDialog.show()
    }
}