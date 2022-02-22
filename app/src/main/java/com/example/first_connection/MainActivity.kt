package com.example.first_connection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.sql.SQLOutput
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = OkHttpClient()
        println("Клиент OkHttp создан")
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/350?api_key=${API.Key}") //
            .build()
        println("Request.Builder создан")
        val gson = Gson()

//Создаем отправку запроса, мы должны имплементировать интерфейс Callback,
//когда будете его импортировать, проверьте, чтобы он был от библиотеки OkHttp, потому что есть
//Интерфейсы с таким же названием и в других библиотеках
        client.newCall(request).enqueue(object : Callback {

            //Переопределяем метод, что будет, если мы не сможем получить ответ на запрос
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                println("Ответ НЕ получен")
            }
            //Переопределяем метод, что будет, если мы сможем получить ответ на запрос
            override fun onResponse(call: Call, response: Response) {
                //Здесь тоже надо обернуть в try-catch
                try {
                    val responseBody = response.body()
                    val card = gson.fromJson(responseBody?.string(), FilmCard::class.java)
                    println("!!! ${card.original_title}")
                    println("!!! ${card.overview}")
                } catch (e: Exception) {
                    println(response)
                    e.printStackTrace()
                }
            }
        })
    }
}