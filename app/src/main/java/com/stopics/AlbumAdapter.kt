package com.stopics

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stopics.activity.AlbumActivity
import com.stopics.model.Album

class AlbumAdapter(private val albumList: List<Album>) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //val ItemsViewModel = mList[position]
        val album  : Album = albumList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.albumTitle.text = album.name
        holder.albumDescription.text =album.description
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.context, AlbumActivity::class.java).apply {
                putExtra("EXTRA_ID", album.id)
            }
            holder.context.startActivity(intent)
        }


        /*holder.button.setOnClickListener {
            val intent = Intent(holder.context, AlbumActivity::class.java).apply {
                putExtra("EXTRA_ID", album.id)
            }
            holder.context.startActivity(intent)
        }*/





    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return albumList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val albumTitle: TextView = itemView.findViewById(R.id.titleView)
        val albumDescription : TextView = itemView.findViewById(R.id.descriptionView)
        //val button: Button = itemView.findViewById(R.id.button_view_album)
        val context: Context = itemView.context
    }
}
