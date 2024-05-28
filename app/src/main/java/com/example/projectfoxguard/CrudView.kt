package com.example.projectfoxguard

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
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CrudView : AppCompatActivity() {
    //Variables for Inputs
    lateinit var boxId: EditText
    lateinit var boxName:EditText
    lateinit var boxType: EditText
    lateinit var boxDate:EditText
    lateinit var boxLocation: EditText
    lateinit var boxDescription:EditText

    //Variables for buttons
    lateinit var Addbtn: Button
    lateinit var Deletebtn: Button
    lateinit var Updatebtn: Button
    lateinit var Searchbtn:Button
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
        boxId = findViewById(R.id.editTextID)
        boxName = findViewById(R.id.editTextName)
        boxType = findViewById(R.id.editTextType)
        boxDate = findViewById(R.id.editTextDate)
        boxLocation = findViewById(R.id.editTextLocation)
        boxDescription = findViewById(R.id.editTextDesc)

        //Variables for Buttons (Add, Delete, Update, and Search)
        Addbtn = findViewById(R.id.btnAdd)
        Deletebtn = findViewById(R.id.btnDelete)
        Updatebtn = findViewById(R.id.btnUpdate)


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

        Deletebtn.setOnClickListener{
            val connection = ConnectionHelper()
            if (connection!=null) {
                try {
                    val eventId = boxId.text.toString().toIntOrNull() // Convert input to Int

                    if (eventId != null) {
                        val deleteEvent: PreparedStatement? = connection.DBConnection()?.prepareStatement("DELETE FROM EVENTSINFO WHERE EventId = ?")
                        if (deleteEvent != null) {
                            deleteEvent.setInt(1, eventId)
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

        Updatebtn.setOnClickListener{
            val connection = ConnectionHelper()
            if(connection!=null) {
                try {
                    val eventId = boxId.text.toString().toIntOrNull()
                    //SQL validation
                    val AddEvent: PreparedStatement = connection.DBConnection()?.prepareStatement("UPDATE EVENTSINFO SET EventName = ?, EventType = ?, EventLocation = ?, EventDate = ?, EventDescription = ? WHERE EventID = ?")!!
                    val eventName = boxName.text.toString()
                    val eventType = boxType.text.toString()
                    val eventLocation = boxLocation.text.toString()
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
        }

    }


}