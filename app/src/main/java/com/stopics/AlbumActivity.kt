package com.stopics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.AlbumJSONFileStorage
import com.stopics.storage.StorageInstance
import com.stopics.storage.StorageList
import java.io.File
import java.lang.Error


class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<PictureViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        var storageAlbum = AlbumJSONFileStorage(this)
        val listStorage = storageAlbum.findAll()
        //Log.e("TAILLE", listStorage.size.toString())

        StorageInstance.init(this);

        var Storage: StorageList<Album> = StorageInstance.get().AlbumList;

        var albums: ArrayList<Album> = Storage.list;
        Log.e("TAILLE", albums.size.toString())



        for (i in 1..albums.size) {
            val album = albums[i - 1]
            Log.e("TITRE", album.name)
            //Log.e("JSON", File("storage_Album.json").readText())
            data.add(PictureViewModel(R.drawable.ic_launcher_foreground, album.name))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = PictureAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


        val button1 = findViewById<Button>(R.id.button_add_picture)

        button1.setOnClickListener {

        }

        val button2 = findViewById<Button>(R.id.button_delete_album)

        button2.setOnClickListener {

        }

        val button3 = findViewById<Button>(R.id.button_modify_album)

        button3.setOnClickListener {

        }
    }
}