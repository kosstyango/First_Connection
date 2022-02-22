package com.example.first_connection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Executors.newSingleThreadExecutor().execute {
            val url = URL("https://reqres.in/api/users/2")
            val connection = url.openConnection() as HttpsURLConnection
            val gson = Gson () // создаём экземпляр класса, чтобы воспользоваться его методами
            try {
                //connection.setRequestProperty("User-Agent", "") //костыль от А.Солдатенкова
                val br = BufferedReader(InputStreamReader(connection.inputStream))
                val line = br.readLine()
                val pers = gson.fromJson(line, Person::class.java)
                println("!!! ${pers.data}")
            } finally {
                connection.disconnect()
            }
        }
    }
}