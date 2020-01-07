package com.ezequieldisisto.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ezequieldisisto.myapplication.GiphyService
import com.ezequieldisisto.myapplication.model.Data
import com.ezequieldisisto.myapplication.model.GiphyObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GiphyViewModel: ViewModel() {

    val giphyList: MutableLiveData<ArrayList<GiphyObj>> = MutableLiveData()
    val status: MutableLiveData<Status> = MutableLiveData()

    fun getList(){
        GiphyService.instance.getGiphyList().enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                status.value =
                    Status.ERROR
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if(response.body() == null){
                    status.value = Status.ERROR
                } else {
                    status.value = Status.SUCCESS
                    response.body()?.data?.let {
                        giphyList.value = ArrayList(it)
                    }

                }
            }
        })
    }

    enum class Status{
        ERROR,
        SUCCESS
    }

}