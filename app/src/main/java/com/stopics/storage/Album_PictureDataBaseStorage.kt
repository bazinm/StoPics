package com.stopics.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.stopics.helper.DataBaseHelper
import com.stopics.model.Album_Picture
import com.stopics.storage.utility.DataBaseStorage

class Album_PictureDataBaseStorage(context : Context) :
    DataBaseStorage<Album_Picture>(DataBaseHelper(context), "Album_Picture"){
        companion object {
            const val ID_ALBUM = 0
            const val ID_PICTURE = 1
        }

    override fun objectToValues(obj: Album_Picture): ContentValues {
        val res = ContentValues()
        res.put(Album_Picture.ID_ALBUM, obj.id_album)
        res.put(Album_Picture.ID_PICTURE, obj.id_picture)

        return res
    }


    override fun cursorToObject(cursor: Cursor): Album_Picture {
        return Album_Picture(
            cursor.getInt(ID_ALBUM),
            cursor.getInt(ID_PICTURE)
        )
    }
}

