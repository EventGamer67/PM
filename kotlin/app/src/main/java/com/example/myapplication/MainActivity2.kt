package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import org.json.JSONArray
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        refresh()
    }

    fun search(){
        var edittext = findViewById<EditText>(R.id.edittext)
    }

    fun add(view:View){
        DataManager.messages.add(Item(name = "namae", phone = "phone", balance = -1, active = true))
        refresh()
    }

    fun edit(item : Item){
        var intent : Intent = Intent(this,EditActivity::class.java)
        intent.putExtra("index", DataManager.messages.indexOf(item))
        startActivity(intent)
        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    fun delete(item : Item){
        DataManager.messages.remove(item)
        refresh()
    }

    fun reverse(view : View){
        DataManager.messages.reverse()
        refresh()
    }

    fun refresh(){
        var itemList : LinearLayout = findViewById(R.id.itemList)
        itemList.removeAllViews()
        for(i in DataManager.messages){
            var tile = layoutInflater.inflate(R.layout.tile,itemList,false)
            tile.findViewById<TextView>(R.id.name).setText(i.name);
            tile.findViewById<TextView>(R.id.balance).setText(i.balance.toString());
            tile.findViewById<TextView>(R.id.phone).setText(i.phone);
            tile.findViewById<TextView>(R.id.active).setText(i.active.toString());
            var btn : Button = tile.findViewById<Button>(R.id.delete)
            btn.setOnClickListener {
                delete(item = i);
            }
            tile.findViewById<Button>(R.id.edit).setOnClickListener{
                edit(item = i)
            }
            itemList.addView(tile)
        }
    }

    fun send(view: View) {

        var jsonobj = JSONObject()
        var jsonarray = JSONArray()

        for(i in DataManager.messages){
            var json = JSONObject()
            json.put("name",i.name);
            json.put("phone",i.phone);
            json.put("balance",i.balance);
            json.put("active",i.active);
            jsonarray.put(json)
        }
        jsonobj.put("items",jsonarray)
        jsonobj.put("type","set")

        DataManager.sendData(data = jsonobj.toString(),context = this)
    }
}