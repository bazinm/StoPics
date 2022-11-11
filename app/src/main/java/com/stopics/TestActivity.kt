package com.stopics

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stopics.model.Album
import com.stopics.storage.AlbumJSONFileStorage
import com.stopics.storage.StorageInstance
import com.stopics.storage.utility.file.JSONFileStorage
import java.io.Console
import java.io.File
import java.io.InputStream
import java.lang.Integer.max
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.jar.Manifest

class TestActivity: AppCompatActivity() {
    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }
    val url = "http://51.68.95.247/gr-3-2/album.json"
    val url_image_1 = "http://51.68.95.247/gr-3-2/ski-1.png"
    val url_image_2 = "http://51.68.95.247/gr-3-2/ski-2.jpg"
    lateinit var imageDownloadView: ImageView
    lateinit var imageView: ImageView
    private var imageUri : Uri? = null




    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testactivity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        imageView = findViewById(R.id.imageView)
        imageDownloadView = findViewById(R.id.imageDownload)
        val btn = findViewById<Button>(R.id.btn)
        val btn_picture = findViewById<Button>(R.id.btn_gallery)
        btn.setOnClickListener(View.OnClickListener {
            Log.e("ICI", "ICI")
            val queue = Volley.newRequestQueue(this)
            queue.add(request)
            queue.start()
        })

        btn_picture.setOnClickListener {
            downloadImage(url_image_1)
            downloadImage(url_image_2)
        }


        /*btn_picture.setOnClickListener {
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
        }*/
    }

    private fun choosePicture(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePicture()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null){
           imageUri = data.data
            Log.d("image url", imageUri.toString())
           imageView.setImageURI(imageUri)
        }
    }



    val request = JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        { res ->

            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show()
            Log.d("JSON", res.toString())
            val json_value = AlbumJSONFileStorage(this)
            val obj = json_value.jsonToObject(res)
            StorageInstance.get().AlbumList.jsonFileStorage.insert(obj)
        },
        { err ->
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
            Log.e("ICI", "ERREUR")}
    )

    var msg : String? = ""
    var lastMsg = ""

    @SuppressLint("Range")
    fun downloadImage(url: String) {
        val directory = File(Environment.DIRECTORY_PICTURES)

        if(!directory.exists()){
            directory.mkdirs()
        }

        val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )

        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL){
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                msg = statusMessage(url, directory, status)

                if(msg != lastMsg) {
                    this.runOnUiThread {
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                    lastMsg = msg ?: ""
                }
                cursor.close()

            }
        }).start()
        Log.e("IMAGE","$directory" + File.separator + url.substring(
        url.lastIndexOf("/") + 1 ))


    }

    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> "There's nothing to download"
        }

        return msg
    }





}