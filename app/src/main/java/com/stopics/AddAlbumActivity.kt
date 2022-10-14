package com.stopics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddAlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_album)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}