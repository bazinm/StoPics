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


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<AlbumViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        var storageAlbum = AlbumJSONFileStorage(this)
        val listStorage = storageAlbum.findAll()
        //Log.e("TAILLE", listStorage.size.toString())

        StorageInstance.init(this);

        var Storage: StorageList<Album> = StorageInstance.get().AlbumList;

        var albums:ArrayList<Album> = Storage.list;
        Log.e("TAILLE", albums.size.toString())



        for (i in 1..albums.size) {
            val album = albums[i-1]
            Log.e("TITRE", album.name)
            //Log.e("JSON", File("storage_Album.json").readText())
            data.add(AlbumViewModel(R.drawable.ic_launcher_foreground, album.name))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = AlbumAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter





        val button1 = findViewById<Button>(R.id.button_add_album)

        button1.setOnClickListener {
            val intent = Intent(this, AddAlbumActivity::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.button_test_activity)

        button2.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }
}