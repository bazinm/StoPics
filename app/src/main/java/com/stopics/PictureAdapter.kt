package com.stopics

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stopics.activity.AlbumActivity
import com.stopics.activity.PictureActivity
import com.stopics.model.Picture
import java.io.File

class PictureAdapter(private val pictureList: List<Picture>) : RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.picture_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val picture = pictureList[position]

        val nameFile = picture.path.split('/').last()
        Log.e("file", nameFile)


        // sets the image to the imageview from our itemHolder class
        val imgFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + picture.path)
        Log.e("PATH", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/ski-2.jpg")
        if(imgFile.exists()){
            val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            holder.imageView.setImageBitmap(imgBitmap)
        }
        else{
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
        //holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)

        // sets the text to the textview from our itemHolder class
        //holder.textView.text = picture.comment
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.context, PictureActivity::class.java).apply {
                putExtra("EXTRA_ID_PICTURE", picture.id)
                putExtra("EXTRA_COMMENT", picture.comment)
                putExtra("EXTRA_PATH", picture.path)
            }
            holder.context.startActivity(intent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return pictureList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        //val textView: TextView = itemView.findViewById(R.id.textView)
        val context: Context = itemView.context
    }
}
