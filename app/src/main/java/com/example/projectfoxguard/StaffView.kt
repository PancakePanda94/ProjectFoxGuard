package com.example.projectfoxguard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StaffView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_staff_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun ViewEvents(view: View?){
        val intent = Intent(this, EventsDisplay::class.java)
        startActivity(intent)
    }
    fun CrudView(view: View?){
        val intent = Intent(this, CrudView::class.java)
        startActivity(intent)
    }

    fun scanQR(view: View?) {
        val intent = Intent(this, StaffScan::class.java)
        intent.putExtra("USER",0)
        startActivity(intent)
    }

}