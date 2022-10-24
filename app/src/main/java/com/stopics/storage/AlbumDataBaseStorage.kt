package com.stopics.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.stopics.helper.DataBaseHelper
import com.stopics.model.Album
import com.stopics.storage.utility.DataBaseStorage

class AlbumDataBaseStorage(context : Context) :
        DataBaseStorage<Album>(DataBaseHelper(context), "Album"){
            companion object {
                const val ID = 0
                const val NAME = 1
                const val IS_SHARED = 2
                const val DESCRIPTION = 3
            }

    override fun objectToValues(obj: Album): ContentValues {
        val res = ContentValues()
        res.put(Album.ID, obj.id)
        res.put(Album.NAME, obj.name)
        res.put(Album.IS_SHARED, obj.is_shared)
        res.put(Album.DESCRIPTION, obj.description)


        return res
    }

    override fun cursorToObject(cursor: Cursor): Album {
        return Album(
            cursor.getInt(ID),
            cursor.getString(NAME),
            cursor.getInt(IS_SHARED) > 0,
            cursor.getString(DESCRIPTION)
        )
    }
}