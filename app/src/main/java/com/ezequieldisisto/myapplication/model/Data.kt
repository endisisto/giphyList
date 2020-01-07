package com.ezequieldisisto.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(val data: List<GiphyObj>) {

}