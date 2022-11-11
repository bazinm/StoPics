package com.stopics.storage

import android.content.Context
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.utility.file.JSONFileStorage
import org.json.JSONArray
import org.json.JSONObject

class AlbumJSONFileStorage(context: Context): JSONFileStorage<Album>(context, "Albumss") {

    override fun create(id:Int, obj: Album): Album {
        return Album(id, obj.name, obj.is_shared, obj.description, obj.pictures_list)
    }

    public override fun objectToJson(id:Int, obj: Album): JSONObject {
        var res = JSONObject()

        var pictures_list = JSONArray()

        for(i in 0 until obj.pictures_list.size){
            var picture = JSONObject()
            picture.put(Picture.ID, obj.pictures_list.get(i).id)
            picture.put(Picture.PATH, obj.pictures_list.get(i).path)
            picture.put(Picture.COMMENT, obj.pictures_list.get(i).comment)

            pictures_list.put(picture)

        }

        res.put(Album.ID, id)
        res.put(Album.NAME, obj.name)
        res.put(Album.IS_SHARED, obj.is_shared)
        res.put(Album.DESCRIPTION, obj.description)
        res.put(Album.PICTURES_LIST, pictures_list)
        return res


    }

    public override fun jsonToObject(json: JSONObject): Album {
        var pictures_list_json = json.getJSONArray(Album.PICTURES_LIST)
        var pictures_list = ArrayList<Picture>();

        for(i in 0 until pictures_list_json.length()){
            var pictureJson = pictures_list_json.getJSONObject(i)
            var picture = Picture(pictureJson.getInt(Picture.ID), pictureJson.getString(Picture.PATH), pictureJson.getString(Picture.COMMENT))
            pictures_list.add(picture)
        }

        return Album(
            json.getInt(Album.ID),
            json.getString(Album.NAME),
            json.getBoolean(Album.IS_SHARED),
            json.getString(Album.DESCRIPTION)
            ,pictures_list
        )
    }
}
