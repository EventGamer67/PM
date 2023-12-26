package com.example.myapplication

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
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
            socket = Socket("127.0.0.1",8888)
        }

        fun sendData(data:String,context:Context){
            GlobalScope.launch {
                try{
                    if(!socket.isConnected){
                        connect(context)
                    }
                    socket.getOutputStream().bufferedWriter().use { it.write(data) }
                    android.widget.Toast.makeText(context,"sended",Toast.LENGTH_SHORT).show()
                    socket.close()

                }catch(e:Exception){
                    android.widget.Toast.makeText(context,"failed ${e.toString()}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

data class User(var name: String, var password : String) : Serializable
data class Item(var text: String)