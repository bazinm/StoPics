package com.stopics.model

class Album(
    val id: Int,
    val name: String,
    val is_shared: Boolean,
    val description: String,
    val pictures_list: ArrayList<Picture>
) {
    companion object {
        const val ID ="id"
        const val NAME = "name"
        const val IS_SHARED ="is_shared"
        const val DESCRIPTION = "description"
        const val PICTURES_LIST = "pictures_list"
    }

    override fun toString(): String {
        var res = "name : " + this.name + "\n"
        res = res + "description : " + this.description
        for(i in 0 until this.pictures_list.size){
            res = res + this.pictures_list[i].toString()
        }
        return res
    }

}