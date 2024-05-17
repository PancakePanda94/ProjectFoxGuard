package com.example.projectfoxguard

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.sql.PreparedStatement
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.Date

class UserScan : AppCompatActivity() {

    private var ConnectionHelper = ConnectionHelper()
    private val usertype=0
    //val dbConfig = DatabaseConfiguration(URL, USER_NAME, PASSWORD)
    private val requestPermissionLauncher=
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted: Boolean ->
            if(isGranted){
                showCamera()
            }else{

            }
        }

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_scan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun setResult(string: String){
        //binding.textResult.text = string
        val scannedData: String = string
        val connection = ConnectionHelper.DBConnection()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        System.out.println(" C DATE is  "+currentDate)

        if(connection!=null) {
            try {
                val matricula: PreparedStatement = ConnectionHelper.DBConnection()
                    ?.prepareStatement("UPDATE ATTENDANCE SET exitTime=$currentDate) WHERE attendantId= AND")!!

                matricula.executeQuery()
                Toast.makeText(this, "Estudiante ENCONTRADO", Toast.LENGTH_SHORT).show()

                // val asistencia: PreparedStatement = ConnectionHelper.DBConnection()?.prepareStatement("INSERT INTO ATTENDANCE ")
            } catch (ex: SQLException) {
                Toast.makeText(this, "Estudiante NO ENCONTRADO", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", ex.message.toString())
            }
        }else{
            Toast.makeText(this, "Database connection not available", Toast.LENGTH_SHORT).show()
        }
    }

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
}