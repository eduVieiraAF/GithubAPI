package com.example.githubapi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapi.di.RetroService
import com.example.githubapi.model.RecyclerList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mService: RetroService

    private var liveDataList: MutableLiveData<RecyclerList>

    // region Pagination
    private var page = 0
    private var isLoading = false
    // endregion

    init {

        (application as MyApplication).getRetroComponent().inject(this)
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): LiveData<RecyclerList> {

        return liveDataList
    }

    fun makeAPICal(queryParam: String = "kotlin", paginating: Boolean = true) {

        //to avoid overlapping
        if (isLoading) return
        isLoading = true

        // simple manual pagination
        if (paginating.not()) {
            page = 0
        }

        val call: Call<RecyclerList>? = mService.getAPIData(queryParam, ++page)

        call?.enqueue(object : Callback<RecyclerList> {

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {

                liveDataList.postValue(null)
                isLoading = false // for next request
            }

            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {

                if (response.isSuccessful) {

                    liveDataList.postValue(response.body())
                }
                else {

                    liveDataList.postValue(null)
                }
                isLoading = false // for next request
            }
        })
    }

}