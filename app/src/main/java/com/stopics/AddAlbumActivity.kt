package com.stopics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.AlbumJSONFileStorage
import java.io.File

class AddAlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_album)
        val btnAdd = findViewById<Button>(R.id.create_album)
        btnAdd.setOnClickListener (View.OnClickListener {
            addAlbum()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun addAlbum(){
        val title_text = findViewById<EditText>(R.id.title_add_album)
        var storageAlbum = AlbumJSONFileStorage(this)
        var newAlbum = Album(1, title_text.text.toString(), true, "", mutableListOf<Picture>())
        storageAlbum.insert(newAlbum)

        var test_storage = AlbumJSONFileStorage(this)
        var list_storage = test_storage.findAll()

        //Log.d("READ", list_storage[0].toString())

    }
}