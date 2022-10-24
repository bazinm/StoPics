package com.stopics.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.stopics.helper.DataBaseHelper
import com.stopics.model.Picture
import com.stopics.storage.utility.DataBaseStorage

class PictureDataBaseStorage(context: Context) :
        DataBaseStorage<Picture>(DataBaseHelper(context), "picture"){
                companion object {
                        const val ID = 0
                        const val PATH = 1
                        const val COMMENT = 2
                }

        override fun objectToValues(obj: Picture): ContentValues {
                val res = ContentValues()
                res.put(Picture.ID, obj.id)
                res.put(Picture.PATH, obj.path)
                res.put(Picture.COMMENT, obj.comment)

                return res
        }

        override fun cursorToObject(cursor: Cursor): Picture {
                return Picture(
                        cursor.getInt(ID),
                        cursor.getString(PATH),
                        cursor.getString(COMMENT)
                )
        }

}