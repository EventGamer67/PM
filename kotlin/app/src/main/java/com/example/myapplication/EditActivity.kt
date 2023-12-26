package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        findViewById<EditText>(R.id.newtext).setText(DataManager.messages[intent.getIntExtra("index",0)].text)
    }

    fun save(view: View) {
        DataManager.messages[intent.getIntExtra("index",0)].text = findViewById<EditText>(R.id.newtext).text.toString()
        onBackPressed()
        finish()
    }
}