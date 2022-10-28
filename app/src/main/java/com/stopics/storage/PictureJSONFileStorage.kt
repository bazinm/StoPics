package com.stopics.storage

import android.content.Context
import com.stopics.model.Album
import com.stopics.model.Picture
import com.stopics.storage.utility.file.JSONFileStorage
import org.json.JSONObject

class PictureJSONFileStorage(context: Context): JSONFileStorage<Picture>(context, "Picture") {

    override fun create(id: Int, obj: Picture): Picture {
        return Picture(id, obj.path, obj.comment)
    }

    public override fun objectToJson(id: Int, obj: Picture): JSONObject {
        var res = JSONObject()
        res.put(Picture.ID, id)
        res.put(Picture.PATH, obj.path)
        res.put(Picture.COMMENT, obj.comment)
        return res
    }

    public override fun jsonToObject(json: JSONObject): Picture {
        return Picture(
            json.getInt(Picture.ID),
            json.getString(Picture.PATH),
            json.getString(Picture.COMMENT)

        )
    }

}