package com.stopics

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
        for (i in 1..20) {
            data.add(AlbumViewModel(R.drawable.ic_launcher_foreground, "Item " + i))
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