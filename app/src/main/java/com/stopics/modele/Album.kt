package com.stopics.model

class Album(
    val id: Int,
    val name: String,
    val is_shared: Boolean,
    val description: String,
    //val pictures_list: MutableList<Picture>?
) {
    companion object {
        const val ID ="id"
        const val NAME = "name"
        const val IS_SHARED ="is_shared"
        const val DESCRIPTION = "description"
        const val PICTURES_LIST = "pictures_list"
    }
}