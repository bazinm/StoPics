package com.stopics

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.stopics.storage.utility.file.JSONFileStorage
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
            //queue.add(request)
            queue.start()
        })


        btn_picture.setOnClickListener {
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
            json_value.insert(obj)
            Log.d("OBJECT", obj.toString())
            val list_storage = json_value.findAll()

            Log.d("READ", list_storage[0].toString())

        },
        { err ->
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
            Log.e("ICI", "ERREUR")}
    )



    /*val requestDownload = ImageRequest()
        ImageRequest(
        url_image_1,
        Response.Listener<Bitmap>() {
            fun onResponse(bitmap: Bitmap) {
                imageDownloadView.setImageBitmap(bitmap)
                Log.d("IMG", "GAGNE")
            }
        }, 0, 0, null,
        Response.ErrorListener() {
            override fun onErrorResponse(error: VolleyError){
                Log.d("IMG", "erreur")

            }

        })*/





}