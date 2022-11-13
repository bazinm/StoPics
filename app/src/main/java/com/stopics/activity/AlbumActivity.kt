package com.stopics.activity

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stopics.AlbumAdapter
import com.stopics.PictureAdapter
import com.stopics.PictureViewModel
import com.stopics.R
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.AlbumJSONFileStorage
import com.stopics.storage.StorageInstance
import com.stopics.storage.StorageList


class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album)
        val id = intent.extras?.getInt("EXTRA_ID")

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Picture>()

        // This loop will create 20 Views containing
        // the image with the count of view
        var storageAlbum = AlbumJSONFileStorage(this)
        val listStorage = storageAlbum.findAll()
        //Log.e("TAILLE", listStorage.size.toString())

        StorageInstance.init(this);

        var storage: StorageList<Album> = StorageInstance.get().AlbumList;
        val album : Album = storage.list.find {
            it.id == id
        } as Album
        //val album: Album = AlbumJSONFileStorage(this).find(id as Int) as Album


        //val album: Album = storage.list[(id as Int) - 1]

        val name : TextView = findViewById(R.id.album_title)
        val description : TextView = findViewById(R.id.album_description)

        name.text = album.name
        description.text = album.description

        for (picture in album.pictures_list) {
            val titre = picture.path
            //Log.e("JSON", File("storage_Album.json").readText())
            data.add(picture)
        }

        // This will pass the ArrayList to our Adapter
        val adapter = PictureAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


        val buttonAdd = findViewById<Button>(R.id.button_add_picture)

        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddPictureActivity::class.java).apply {
                putExtra("EXTRA_ID", album.id)
            }
            startActivity(intent)


        }

        val buttonDelete = findViewById<Button>(R.id.button_delete_album)

        buttonDelete.setOnClickListener {
            StorageInstance.get().AlbumList.jsonFileStorage.delete(album.id)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        val buttonEdit = findViewById<Button>(R.id.button_modify_album)

        buttonEdit.setOnClickListener {

        }
    }
}