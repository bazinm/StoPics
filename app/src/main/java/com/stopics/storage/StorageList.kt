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
            Log.e("TEST", elem.toString())
            list.add(elem)
        }
        Log.d(jsonFileStorage.fileName + "_restore", list.size.toString())
    }


    override fun fetch() {

        if(list.size == 0) {
            this.onStartFetch()
            Log.d("Test","Fetch")
            val request = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                { res ->
                    this.onFetchSuccess()
                    Log.d("Fetch Success", res.length().toString())
                    for (i in 0 until res.length()) {
                        val obj = jsonFileStorage.jsonToObject(res.getJSONObject(i))
                        if (list.indexOf(obj) == -1) {

                            list.add(obj)
                            jsonFileStorage.insert(obj)
                            Log.d("listFet",list.toString())
                        }
                    }
                    jsonFileStorage.write()
                    Log.d("aled",jsonFileStorage.toString())
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
