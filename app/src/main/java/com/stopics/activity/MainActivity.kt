package com.stopics.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stopics.AlbumAdapter
import com.stopics.AlbumViewModel
import com.stopics.R
import com.stopics.model.Album
import com.stopics.storage.AlbumJSONFileStorage
import com.stopics.storage.StorageInstance
import com.stopics.storage.StorageList


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        recyclerview.layoutManager = GridLayoutManager(this, 3)

        val data = ArrayList<Album>()

        StorageInstance.init(this);

        val storage: StorageList<Album> = StorageInstance.get().AlbumList;

        val albums:ArrayList<Album> = storage.list;

        val btnAddEmpty = findViewById<Button>(R.id.button_add_album_empty)

        val buttonAddAlbum = findViewById<Button>(R.id.button_add_album)

        btnAddEmpty.visibility = View.GONE

        if(albums.size == 0){
            btnAddEmpty.visibility = View.VISIBLE
            btnAddEmpty.setOnClickListener {
                val intent = Intent(this, AddAlbumActivity::class.java)
                startActivity(intent)
            }

        }

        for (i in 1..albums.size) {
            val album = albums[i-1]
            data.add(album)
        }

        val adapter = AlbumAdapter(data)

        recyclerview.adapter = adapter


        buttonAddAlbum.setOnClickListener {
            val intent = Intent(this, AddAlbumActivity::class.java)
            startActivity(intent)
        }

    }
}