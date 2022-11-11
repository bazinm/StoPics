package com.stopics.storage

import android.content.Context
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.stopics.model.Album


class StorageInstance(ctx: Context) {

    val ctx: Context = ctx
    var queue = Volley.newRequestQueue(ctx)

    companion object {
        var instance: StorageInstance? = null
        fun init(ctx: Context){
            instance = StorageInstance(ctx)
            (instance as StorageInstance).AlbumList.restore()
            (instance as StorageInstance).AlbumList.fetch()
        }
        fun get():StorageInstance {
            if(instance==null){
                throw java.lang.Exception("Vous n'avez pas initié la classe StorageInstance")
            }
            return instance as StorageInstance
        }
    }

    fun add(request: JsonArrayRequest){
        queue = Volley.newRequestQueue(ctx)
        queue.add(request)
        queue.start()
    }

    val AlbumList: StorageList<Album> = StorageList(
        "http://51.68.95.247/gr-3-2/album.json",
        AlbumJSONFileStorage(ctx)
    )
}