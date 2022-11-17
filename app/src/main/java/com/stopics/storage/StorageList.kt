package com.stopics.storage

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.stopics.storage.utility.file.JSONFileStorage
import org.json.JSONObject
import kotlin.math.log

class StorageList<T>(
    url:String,
    jsonFileStorage: JSONFileStorage<T>
    ):StorageElement<T>(url,jsonFileStorage) {

    val list: ArrayList<T> = ArrayList()

    override fun restore() {
        jsonFileStorage.read()
        val listAll = jsonFileStorage.findAll()
        for (elem in listAll) {
            list.add(elem)
        }
    }


    override fun fetch() {

        if(list.size == 0) {
            this.onStartFetch()
            val request = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                { res ->
                    this.onFetchSuccess()
                    for (i in 0 until res.length()) {
                        val obj = jsonFileStorage.jsonToObject(res.getJSONObject(i))
                        if (list.indexOf(obj) == -1) {

                            list.add(obj)
                            jsonFileStorage.insert(obj)
                        }
                    }
                    jsonFileStorage.write()
                },
                { err ->
                    err.printStackTrace()
                    this.onFetchError()
                }
            )
            StorageInstance.get().add(request)
        }
    }

}
