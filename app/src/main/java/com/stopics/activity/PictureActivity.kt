package com.stopics.activity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.stopics.R
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.AlbumJSONFileStorage
import com.stopics.storage.StorageInstance
import com.stopics.storage.StorageList
import java.io.File

class PictureActivity : AppCompatActivity() {
    val id : Int? = 0;
    val album : Album? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        val imageView = findViewById<ImageView>(R.id.picture_view)
        val commentView = findViewById<TextView>(R.id.comment_text)

        val comment = intent.extras?.getString("EXTRA_COMMENT") as String
        val path = intent.extras?.getString("EXTRA_PATH") as String
        val id = intent.extras?.getInt("EXTRA_ID_PICTURE") as Int
        var picture = Picture(id, path, comment)

        var storage: StorageList<Album> = StorageInstance.get().AlbumList;


        val imgFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + path)
        val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        imageView.setImageBitmap(imgBitmap)

        commentView.setText(comment)


    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean{
        finish()
        return true
    }
}