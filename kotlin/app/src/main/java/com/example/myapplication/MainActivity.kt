package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View){
        var name : String = findViewById<TextView>(R.id.login).text.toString()
        var pass : String = findViewById<TextView>(R.id.password).text.toString()

        var intent : Intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)
        finish()
    }
}