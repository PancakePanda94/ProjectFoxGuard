package com.example.projectfoxguard

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfoxguard.databinding.ActivityUserScanBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.sql.PreparedStatement
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.Date

class UserScan : AppCompatActivity() {

    private var ConnectionHelper = ConnectionHelper()

    //val dbConfig = DatabaseConfiguration(URL, USER_NAME, PASSWORD)
    @RequiresApi(Build.VERSION_CODES.O)
    private val requestPermissionLauncher=
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted: Boolean ->
            if(isGranted){
                showCamera()
            }else{
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
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

    private lateinit var binding: ActivityUserScanBinding

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
        //setContentView(R.layout.activity_user_scan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
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
        binding = ActivityUserScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setResult(string: String){
        //binding.textResult.text = string
        val scannedData: String = string
        val connection = ConnectionHelper.DBConnection()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = sdf.format(Date())
        val matricula = intent.getStringExtra("Matricula")

        if(connection!=null) {
            var attendance: PreparedStatement? = null
            try {
                val query = """
                UPDATE ATTENDANCE
                SET exitTime = ?
                WHERE attendantId IN (
                    SELECT TOP 1 attendantId
                    FROM ATTENDANCE
                    WHERE attendeeId = ?
                    ORDER BY attendantId DESC
                )"""

                attendance = connection.prepareStatement(query)
                attendance.setString(1, currentDate)
                attendance.setString(2, matricula)

                val rowsUpdated = attendance.executeUpdate()
                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Estudiante ENCONTRADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Estudiante NO ENCONTRADO", Toast.LENGTH_SHORT).show()
                }

                // val asistencia: PreparedStatement = ConnectionHelper.DBConnection()?.prepareStatement("INSERT INTO ATTENDANCE ")
            } catch (ex: SQLException) {
                Toast.makeText(this, "Estudiante NO ENCONTRADO", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", ex.message.toString())
            }
        }else{
            Toast.makeText(this, "Database connection not available", Toast.LENGTH_SHORT).show()
        }
    }


}