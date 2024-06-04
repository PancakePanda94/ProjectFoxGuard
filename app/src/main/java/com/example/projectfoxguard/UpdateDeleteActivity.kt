package com.example.projectfoxguard

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.Executors

class UpdateDeleteActivity : AppCompatActivity() {
    private var ConnectionHelper = ConnectionHelper()
    lateinit var Deletebtn: Button
    lateinit var Updatebtn: Button
    private lateinit var editTextEventName: EditText
    private lateinit var editTextEventType: EditText
    private lateinit var editTextEventLocation: EditText
    private lateinit var editTextEventDate: Button
    private lateinit var editTextEventTime: Button
    private lateinit var editTextEventDescription: EditText
    private var eventDateTime: Calendar = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_delete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextEventName = findViewById(R.id.editTextName)
        editTextEventType = findViewById(R.id.editTextType)
        editTextEventLocation = findViewById(R.id.editTextLocation)
        editTextEventDate = findViewById(R.id.btnDatePicker)
        editTextEventTime = findViewById(R.id.btnTimePicker)
        editTextEventDescription = findViewById(R.id.editTextDesc)

        // Get the event ID from the intent
        val eventId = intent.getStringExtra("EVENT_ID")

        // Fetch event details
        if (eventId != null) {
            fetchEventDetails(eventId)
        } else {
            Toast.makeText(this, "Event ID is missing", Toast.LENGTH_SHORT).show()
        }

        Deletebtn = findViewById(R.id.btnDelete)
        Updatebtn = findViewById(R.id.btnUpdate)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        val btnTimePicker: Button = findViewById(R.id.btnTimePicker)
        //val tvSelectedDate: TextView = findViewById(R.id.tvSelectedDate)

        btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
        btnTimePicker.setOnClickListener {
            showTimePickerDialog()
        }

        Deletebtn.setOnClickListener{
            val connection = ConnectionHelper()
            if (connection!=null) {
                try {
                    //val eventId = boxId.text.toString().toIntOrNull() // Convert input to Int

                    if (eventId != null) {
                        val deleteEvent: PreparedStatement? = connection.DBConnection()?.prepareStatement("DELETE FROM EVENTSINFO WHERE EventId = ?")
                        if (deleteEvent != null) {
                            deleteEvent.setInt(1, eventId.toInt())
                        }
                        val rowsAffected = deleteEvent?.executeUpdate()

                        if (rowsAffected != null) {
                            if (rowsAffected > 0) {
                                Toast.makeText(this, "Event has been deleted successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "No event found with the provided ID", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Please enter a valid Event ID", Toast.LENGTH_SHORT).show()
                    }
                } catch (ex: SQLException) {
                    Toast.makeText(this, "Deletion Error: ${ex.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /* Updatebtn.setOnClickListener{
            val connection = ConnectionHelper()
            if(connection!=null) {
                try {
                    //val eventId = boxId.text.toString().toIntOrNull()
                    //SQL validation
                    val AddEvent: PreparedStatement = connection.DBConnection()?.prepareStatement("UPDATE EVENTSINFO SET EventName = ?, EventType = ?, EventLocation = ?, EventDate = ?, EventDescription = ? WHERE EventID = ?")!!
                    val eventName = editTextEventName.text.toString()
                    val eventType = editTextEventType.text.toString()
                    val eventLocation = editTextEventLocation.text.toString()
                    val eventDate = boxDate.text.toString()
                    val inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

                    // Parse the input date string to a LocalDate object
                    val parsedDate = LocalDate.parse(eventDate, inputFormatter)

                    // Convert the LocalDate to a LocalDateTime
                    val parsedDateTime = parsedDate.atStartOfDay()

                    // Format the LocalDateTime object to the desired output format
                    val sqlFormattedDate = parsedDateTime.format(outputFormatter)

                    val eventDescription = boxDescription.text.toString()

                    AddEvent.setString(1, eventName)
                    AddEvent.setString(2, eventType)
                    AddEvent.setString(3, eventLocation)
                    AddEvent.setString(4, sqlFormattedDate)
                    AddEvent.setString(5, eventDescription)

                    if (eventId != null) {
                        AddEvent.setInt(6, eventId)
                    }
                    //Content to be added
                    //AddEvent.setString(2, boxName.text.toString())
                    //AddEvent.setString(3, boxType.text.toString())
                    //AddEvent.setString(4, boxDate.text.toString())
                    //AddEvent.setString(5, boxLocation.text.toString())
                    //AddEvent.setString(6, boxDescription.text.toString())
                    AddEvent.executeUpdate()

                    Toast.makeText(this, "Event Has been updated successfully", Toast.LENGTH_SHORT)
                        .show()
                } catch (ex: SQLException) {
                    Toast.makeText(this, "Update Error : ${ex.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } */
        Updatebtn.setOnClickListener{
            if (eventId != null) {
                updateEvent(eventId)
            } else {
                Toast.makeText(this, "Event ID is missing", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the date as you like
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
               //tvSelectedDate.text = selectedDate
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                // Handle the selected time here
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                // Use the selected time (e.g., save it, send it to another activity, etc.)
            },
            hour,
            minute,
            true // Use 24-hour format
        )
        timePickerDialog.show()
    }
    */
    private fun updateEvent(eventId: String) {
        val connection = ConnectionHelper.DBConnection()
        if (connection != null) {
            try {
                val updateQuery = "UPDATE EVENTSINFO SET EventName = ?, EventType = ?, EventLocation = ?, EventDate = ?, EventDescription = ? WHERE EventID = ?"
                val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

                val eventName = editTextEventName.text.toString()
                val eventType = editTextEventType.text.toString()
                val eventLocation = editTextEventLocation.text.toString()
                val eventDescription = editTextEventDescription.text.toString()

                // Format the date and time from the Calendar object
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val sqlFormattedDate = sdf.format(eventDateTime.time)

                preparedStatement.setString(1, eventName)
                preparedStatement.setString(2, eventType)
                preparedStatement.setString(3, eventLocation)
                preparedStatement.setString(4, sqlFormattedDate)
                preparedStatement.setString(5, eventDescription)
                preparedStatement.setString(6, eventId)

                preparedStatement.executeUpdate()

                Toast.makeText(this, "Event has been updated successfully", Toast.LENGTH_SHORT).show()
            } catch (ex: SQLException) {
                Toast.makeText(this, "Update Error: ${ex.message}", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", ex.message.toString())
            } finally {
                try {
                    connection.close()
                } catch (ex: SQLException) {
                    Log.e("Error: ", ex.message.toString())
                }
            }
        } else {
            Toast.makeText(this, "Database connection not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchEventDetails(eventId: String) {
        // Use a background thread to fetch data
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val connection: Connection? = ConnectionHelper.DBConnection()
            if (connection != null) {
                try {
                    val query = "SELECT eventName, eventType, eventLocation, eventDate, eventDescription FROM EVENTSINFO WHERE eventId = ?"
                    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
                    preparedStatement.setString(1, eventId)
                    val resultSet: ResultSet = preparedStatement.executeQuery()

                    if (resultSet.next()) {
                        val eventName = resultSet.getString("eventName")
                        val eventType = resultSet.getString("eventType")
                        val eventLocation = resultSet.getString("eventLocation")
                        val eventDate = resultSet.getString("eventDate")
                        val eventDescription = resultSet.getString("eventDescription")

                        handler.post {
                            editTextEventName.setText(eventName)
                            editTextEventType.setText(eventType)
                            editTextEventLocation.setText(eventLocation)
                            editTextEventDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(eventDateTime.time)
                            editTextEventTime.text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(eventDateTime.time)
                            editTextEventDescription.setText(eventDescription)
                        }
                    } else {
                        handler.post {
                            Toast.makeText(this@UpdateDeleteActivity, "Event not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (ex: SQLException) {
                    handler.post {
                        Toast.makeText(this@UpdateDeleteActivity, "Error fetching event details", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@UpdateDeleteActivity, "Database connection not available", Toast.LENGTH_SHORT).show()
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
            editTextEventDate.append(" " + sdf.format(eventDateTime.time))
        }, hour, minute, true)

        timePickerDialog.show()
    }
}