package com.stopics.activity

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.stopics.R
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.StorageInstance
import com.stopics.storage.StorageList
import java.lang.Exception

class AddPictureActivity : AppCompatActivity() {
    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    lateinit var imageView: ImageView
    private var imageUri : Uri? = null
    private var path : String? = null
    var id : Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_picture)

        id = intent.extras?.getInt("EXTRA_ID")

        var storage: StorageList<Album> = StorageInstance.get().AlbumList;
        val album : Album = storage.list.find {
            it.id == id
        } as Album

        imageView = findViewById(R.id.image_add_picture)

        val comment = findViewById<EditText>(R.id.comment_add_picture)
        val btn = findViewById<Button>(R.id.choose_image)
        val btn_create = findViewById<Button>(R.id.create_picture)

        btn.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)

                }
                else{
                    choosePicture();
                }
            }
            else{
                choosePicture();
            }
        }

        btn_create.setOnClickListener {
            addPicture(album)
            val intent = Intent(this, AlbumActivity::class.java).apply {
                putExtra("EXTRA_ID", album.id)
            }
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean{
        val intent = Intent(this, AlbumActivity::class.java).apply {
            putExtra("EXTRA_ID", id)
        }
        startActivity(intent)
        return true
    }

    private fun choosePicture(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, TestActivity.IMAGE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null){
            imageUri = data.data
            path  = getFileName(imageUri, this)

            Log.e("path", getFileName(imageUri, this))
            Log.d("image url", imageUri.toString())
            imageView.setImageURI(imageUri)
        }
    }

    @SuppressLint("Range")
    fun getFileName(uri : Uri?, context : Context) : String {
        var res : String = ""
        if(uri != null){


            if(uri.scheme.equals("content")){ val cursor = context.contentResolver.query(uri, null, null, null, null)
                if(cursor != null && cursor.moveToFirst()){
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    cursor.close()
                }


            }
        }
        return res
    }

    private fun addPicture(album : Album){
        val commentPicture = findViewById<EditText>(R.id.comment_add_picture)
        var newPicture =  Picture(1, path.toString(), commentPicture.text.toString())
        album.pictures_list.add(newPicture)
        StorageInstance.get().AlbumList.jsonFileStorage.update(album.id, album)
    }

}