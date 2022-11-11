package com.stopics.storage

import com.stopics.storage.utility.file.JSONFileStorage


abstract class StorageElement<T>(url:String, jsonFileStorage: JSONFileStorage<T>) {

    val jsonFileStorage: JSONFileStorage<T> = jsonFileStorage

    val url: String = url;
    var success: Boolean = false
    var loading: Boolean = false
    var fetched: Boolean = false

    abstract fun fetch()
    public abstract fun restore()

    protected fun onStartFetch(){
        success = false
        loading = true
        fetched = false
    }

    protected fun onFetchSuccess(){
        success = true
        loading = false
        fetched = true
    }

    protected fun onFetchError(){
        success = false
        loading = false
        fetched = true
    }
}