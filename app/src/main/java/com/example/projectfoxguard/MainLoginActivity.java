package com.example.projectfoxguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainLoginActivity extends AppCompatActivity {
    EditText mEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mEdit   = (EditText)findViewById(R.id.userId);
    }

    public void LoginStaff(View view){
        Intent intent=new Intent(this, StaffView.class);
        startActivity(intent);
    }

    public void LoginUser(View view){
        Intent intent=new Intent(this, UserView.class);

       /* ResultSet resultSet = null;
        try  {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(JavaConnectionHelper.connectionUrl);
            Statement statement = connection.createStatement();

            String selectSql = "SELECT TOP 1 * StudentId from ATTENDEES WHERE StudentId ="+mEdit.getText().toString();
            resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                Toast.makeText(this, "Estudiante ENCONTRADO", Toast.LENGTH_SHORT).show();
                intent.putExtra("StudentId",resultSet.getString(1));
            }
        }
        catch (/* SQLException | ClassNotFoundException |  Exception e) {
            e.printStackTrace();
        }
        */
        startActivity(intent);
    }



}