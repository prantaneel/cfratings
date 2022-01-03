package com.example.cf_ratings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn1)
        val txt = findViewById<EditText>(R.id.Text1)
        var codeforcesHandle : String
        btn.setOnClickListener{
            codeforcesHandle = txt.text.toString()
//            val toast = Toast.makeText(applicationContext, codeforcesHandle, Toast.LENGTH_LONG)
//            toast.show()
            val intent = Intent(applicationContext, MainActivity2::class.java)
            intent.putExtra("message_key",codeforcesHandle)
            startActivity(intent)
        }
    }
}