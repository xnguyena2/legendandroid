package com.example.coffeordering.ui.main.Socket

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.Socket
import java.net.UnknownHostException
import android.system.Os.socket
import java.io.BufferedReader


object MySocket {
    lateinit var hostIP: String

    val port: Int = 8888

    fun setIP(ip: String, done: (String)->Unit, err: ()->Unit){
        this.hostIP = ip
        sendMSD("PING", done, err)
    }

    fun sendMSD(msg: String, done: (String)->Unit, err: ()->Unit){

        try {
            Socket(hostIP, port).use { socket ->

                socket.getOutputStream().write("${msg}<EOF>".toByteArray(Charsets.UTF_8))

                val input: InputStream = socket.getInputStream()
                var character: Int
                val data = StringBuilder()
                val reader = BufferedReader(InputStreamReader(input, Charsets.UTF_8))
                while (reader.read().also { character = it } != -1) {
                    data.append(character.toChar())
                }
                println(data)
                Log.i("last recive data", "data: "+data)
                Log.e("EEEEEEEEEEEEEEEEEEE", "Close socket!")

                done(data.toString())
            }
        } catch (ex: UnknownHostException) {
            Log.e("EEEEEEEEEEEEEEEEEEE", "Server not found: " + ex.message)
            err()
        } catch (ex: IOException) {
            Log.e("EEEEEEEEEEEEEEEEEEE", "I/O error: " + ex.message)
            err()
        } finally {
        }
    }
}