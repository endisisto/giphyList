package com.ezequieldisisto.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiphyObj(val title: String,
                    val images: Image,
                    val import_datetime: String) {

    fun getImage(): String{
        return images.original.url
    }
}