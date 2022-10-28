package com.stopics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stopics.model.Album
import com.stopics.storage.AlbumJSONFileStorage
import com.stopics.storage.utility.file.JSONFileStorage

class TestActivity: AppCompatActivity() {
    val url = "http://51.68.95.247/gr-3-2/album.json"





    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testactivity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener(View.OnClickListener {
            Log.e("ICI", "ICI")
            val queue = Volley.newRequestQueue(this)
            queue.add(request)
            queue.start()
        })
    }


    val request = JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        { res ->

            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show()
            Log.d("JSON", res.toString())
            val json_value = AlbumJSONFileStorage(this)
            json_value.write()
            val obj = json_value.jsonToObject(res)
            Log.d("OBJECT", obj.toString())
            val read_obj = AlbumJSONFileStorage(this).find(1)
            Log.d("READDDDD", read_obj.toString())



        },
        { err ->
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
            Log.e("ICI", "ERREUR")}
    )

}