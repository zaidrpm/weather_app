package com.example.weather

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.random.Random

data class d(val temp:Float,
             val feels_like:Float,
             val temp_min:Float,
             val temp_max:Float,
             val pressure:Int,
             val humidity:Int
             )

data class js2(val main:d)

class MainActivity : AppCompatActivity() {
fun clicked(view: View)
{
    findViewById<Button>(R.id.bt).isEnabled = false
    val city:String=findViewById<EditText>(R.id.et).text.toString()
    GlobalScope.launch(Dispatchers.Default){
        okh(city)
    }
}

suspend fun okh(city:String="London")
{
    val url="https://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=cc7e873646b5bf13b25631beabcaf07f"
    //val url="https://zaidrpm.bitbucket.io/test1.html"
    val request= Request.Builder().url(url).build()
    val client= OkHttpClient()
    var response_str=""
    try {
        val response = client.newCall(request).execute()
        response_str = response.body!!.string()
    }
    catch(e:Exception)
    {
        println("Info, Okhttp Request Failed")
    }
   println("Info,"+response_str)


    try {
        val gson= Gson()
        println("Info,In2")
        var testModel = gson.fromJson(response_str, js2::class.java)
        withContext(Dispatchers.Main)
        {
            findViewById<TextView>(R.id.result).text=testModel.main.temp.toString()
        }
        println("Info,"+testModel.toString())
        println(testModel)
    }
    catch(e:Exception) {
        println("Info,Got it")
        e.printStackTrace()
    }
   
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val im:ImageView=findViewById(R.id.im)
        val r= Random.nextInt(1,6)
        im.setImageResource(when(r) {
            1 -> R.drawable.p1
            2 -> R.drawable.p2
            3 -> R.drawable.p3
            4 -> R.drawable.p4
            5 -> R.drawable.p5
            else -> R.drawable.init
        })
    }
}
