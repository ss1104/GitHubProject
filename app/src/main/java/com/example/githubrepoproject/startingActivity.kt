package com.example.githubrepoproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class startingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setViews()
    }

    private fun setViews() {
        val editText = findViewById<EditText>(R.id.edittext)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val companyName: String = editText.text.toString()
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("Company",companyName)
            startActivity(intent)
        }
    }
}