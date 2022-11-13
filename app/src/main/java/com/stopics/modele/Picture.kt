package com.stopics.model

class Picture(
    var id: Int,
    val path: String,
    val comment: String,
    ) {

    companion object {
        const val ID = "id"
        const val PATH = "path"
        const val COMMENT = "comment"
    }

    override fun toString(): String {
        return this.path + "\n"
    }

}