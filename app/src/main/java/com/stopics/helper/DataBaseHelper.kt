package com.stopics.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.stopics.model.Album
import com.stopics.model.Album_Picture
import com.stopics.model.Picture

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context,"stoPics.db",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Picture (" +
                    "${BaseColumns._ID} INTEGER," +
                    "${Picture.PATH} TEXT," +
                    "${Picture.COMMENT} TEXT," +
                    "PRIMARY KEY(${BaseColumns._ID})" +
                    ")"
        )
        db.execSQL(
            "CREATE TABLE Album (" +
                    "${BaseColumns._ID} INTEGER," +
                    "${Album.NAME} TEXT," +
                    "${Album.IS_SHARED} INT," +
                    "${Album.DESCRIPTION} TEXT," +
                    "PRIMARY KEY(${BaseColumns._ID})" +
                    ")"
        )
        db.execSQL(
            "CREATE TABLE Album_Picture (" +
                    "${Album_Picture.ID_ALBUM} INT," +
                    "${Album_Picture.ID_PICTURE} INT," +
                    "FOREIGN KEY(${Album_Picture.ID_ALBUM}) REFERENCES Album(${BaseColumns._ID})," +
                    "FOREIGN KEY(${Album_Picture.ID_PICTURE}) REFERENCES Picture(${BaseColumns._ID})," +
                    "PRIMARY KEY(${Album_Picture.ID_ALBUM}, ${Album_Picture.ID_PICTURE})" +
                    ")"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}