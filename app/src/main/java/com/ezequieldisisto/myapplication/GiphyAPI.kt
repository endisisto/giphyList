package com.ezequieldisisto.myapplication

import com.ezequieldisisto.myapplication.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyAPI {

    @GET(UrlUtils.SEARCH)
    fun getGiphyList(@Query("api_key") apiKey: String, @Query("q") query: String): Call<Data>
}