package com.example.testing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val et_username = findViewById<EditText>(R.id.et_username)
        val et_password = findViewById<EditText>(R.id.et_password)
        val btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener{
            if (et_username.text.toString() == "user" && et_password.text.toString() == "pass") {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }

    }
}