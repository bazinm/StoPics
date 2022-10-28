package com.stopics.storage

import android.content.Context
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.utility.file.JSONFileStorage
import org.json.JSONObject

class AlbumJSONFileStorage(context: Context): JSONFileStorage<Album>(context, "Album") {

    override fun create(id:Int, obj: Album): Album {
        return Album(id, obj.name, obj.is_shared, obj.description, obj.pictures_list)
    }

    public override fun objectToJson(id:Int, obj: Album): JSONObject {
        var res = JSONObject()
        res.put(Album.ID, id)
        res.put(Album.NAME, obj.name)
        res.put(Album.IS_SHARED, obj.is_shared)
        res.put(Album.DESCRIPTION, obj.description)
        res.put(Album.PICTURES_LIST, obj.pictures_list)
        return res


    }

    public override fun jsonToObject(json: JSONObject): Album {
        var picture_array_json = json.getJSONArray(Album.PICTURES_LIST)
        var picture_array_obj = mutableListOf<Picture>();
        for(i in 0 until picture_array_json.length()){
            picture_array_obj.add(PictureJSONFileStorage(context).jsonToObject(picture_array_json.get(i) as JSONObject))
        }

        return Album(
            json.getInt(Album.ID),
            json.getString(Album.NAME),
            json.getBoolean(Album.IS_SHARED),
            json.getString(Album.DESCRIPTION),
            picture_array_obj
        )
    }
}
