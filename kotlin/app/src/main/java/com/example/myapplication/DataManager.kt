package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable
import java.net.Socket
import java.net.SocketAddress

class DataManager {
    companion object{
        var users : MutableList<User> = mutableListOf()
        var messages : MutableList<Item> = mutableListOf()
        var socket : Socket = Socket()

        fun connect(context: Context){
            socket = Socket("10.0.2.2",8888)
        }

        fun sendData(data:String,context:Context) {
            GlobalScope.launch(Dispatchers.IO) {
                try{
                    if(socket.isClosed || !socket.isConnected){
                        connect(context)
                    }
                    socket.getOutputStream().write(data.toString().toByteArray())
                    socket.close()
                    Log.i("info","sended")

                }catch(e:Exception){
                    Log.e("info",e.toString())
                }
            }
        }
    }
}

data class User(var name: String, var password : String) : Serializable
data class Item(var name: String, var phone : String, var balance : Int, var active : Boolean)