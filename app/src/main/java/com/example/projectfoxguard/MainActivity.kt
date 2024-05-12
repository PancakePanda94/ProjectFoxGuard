package com.example.projectfoxguard

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfoxguard.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
// %use dataframe
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.*
class MainActivity : AppCompatActivity() {

    private var ConnectionHelper = ConnectionHelper()

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

    private lateinit var binding: ActivityMainBinding

    private fun setResult(string: String){
        binding.textResult.text = string
        val scannedData: String = string
        val connection = ConnectionHelper.DBConnection()
        if(connection!=null) {
            try {
                val matricula: PreparedStatement = ConnectionHelper.DBConnection()
                    ?.prepareStatement("SELECT top 1 * FROM ATTENDEES WHERE attendeeId =$scannedData ")!!

                matricula.executeQuery()
                Toast.makeText(this, "Estudiante ENCONTRADO", Toast.LENGTH_SHORT).show()

                //val asistencia: PreparedStatement = ConnectionHelper.DBConnection()?.prepareStatement("INSERT INTO ATTENDANCE ")
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




    }

    private fun initViews() {
        binding.fab.setOnClickListener{
            checkPermissionCamera(this)
        }
    }

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
}