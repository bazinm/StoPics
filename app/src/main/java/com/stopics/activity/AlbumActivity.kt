package com.stopics.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stopics.PictureAdapter
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

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 3)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Picture>()


        StorageInstance.init(this);

        var storage: StorageList<Album> = StorageInstance.get().AlbumList;
        val album : Album = storage.list.find {
            it.id == id
        } as Album
        //val album: Album = AlbumJSONFileStorage(this).find(id as Int) as Album


        //val album: Album = storage.list[(id as Int) - 1]

        val name : TextView = findViewById(R.id.album_title)
        val description : TextView = findViewById(R.id.album_description)

        val nameEdit : EditText = findViewById((R.id.album_title_edit))
        val descriptionEdit : EditText = findViewById((R.id.album_description_edit))

        nameEdit.visibility = View.GONE
        descriptionEdit.visibility = View.GONE

        name.text = album.name
        nameEdit.setText(album.name)
        description.text = album.description
        descriptionEdit.setText(album.description)

        for (picture in album.pictures_list) {
            val titre = picture.path
            //picture.id = id as Int
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
            Toast.makeText(this, R.string.delete, Toast.LENGTH_SHORT).show()

        }

        val buttonEdit = findViewById<Button>(R.id.button_modify_album)
        buttonEdit.visibility = View.GONE


        name.setOnClickListener {
            name.visibility = View.GONE
            nameEdit.visibility = View.VISIBLE
            nameEdit.requestFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)

            buttonEdit.visibility = View.VISIBLE

            buttonEdit.setOnClickListener {
                EditAlbum(album, name, nameEdit, description, descriptionEdit, buttonEdit)

            }

        }

        description.setOnClickListener {
            description.visibility = View.GONE
            descriptionEdit.visibility = View.VISIBLE
            descriptionEdit.requestFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            buttonEdit.visibility = View.VISIBLE

            buttonEdit.setOnClickListener {
                EditAlbum(album, name, nameEdit, description, descriptionEdit, buttonEdit)

            }
        }
    }

    fun EditAlbum(album : Album,name : TextView, nameEdit : EditText, description: TextView, descriptionEdit : EditText, buttonEdit : Button){
        album.name = nameEdit.text.toString()
        album.description = descriptionEdit.text.toString()
        StorageInstance.get().AlbumList.jsonFileStorage.update(album.id, album)

        name.setText(album.name)
        description.setText(album.description)

        name.visibility = View.VISIBLE
        nameEdit.visibility = View.GONE

        description.visibility = View.VISIBLE
        descriptionEdit.visibility = View.GONE

        buttonEdit.visibility = View.GONE
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        Toast.makeText(this, R.string.edit, Toast.LENGTH_SHORT).show()

    }
}