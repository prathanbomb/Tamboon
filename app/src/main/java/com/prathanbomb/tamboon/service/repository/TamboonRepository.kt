package com.prathanbomb.tamboon.service.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.service.model.Request
import com.prathanbomb.tamboon.service.model.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



/**
 * Created by prathanbomb on 9/14/2017 AD.
 */

class TamboonRepository private constructor() {

    private val tamboonService: TamboonService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
                .baseUrl(TamboonService.HTTPS_API_TAMBOON_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        tamboonService = retrofit.create(TamboonService::class.java)
    }

    val charityList: LiveData<List<Charity>>
        get() {
            val data = MutableLiveData<List<Charity>>()

            tamboonService.getCharityList().enqueue(object : Callback<List<Charity>> {
                override fun onResponse(call: Call<List<Charity>>, response: Response<List<Charity>>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<List<Charity>>, t: Throwable) {
                    data.value = null
                }
            })

            return data
        }

    fun makeDonation(request: Request): LiveData<Result> {
        val data = MutableLiveData<Result>()
        tamboonService.makeDonation(request).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    companion object {
        private var tamboonRepository: TamboonRepository? = null
        val instance: TamboonRepository
            @Synchronized get() {
                if (tamboonRepository == null) {
                    tamboonRepository = TamboonRepository()
                }
                return tamboonRepository as TamboonRepository
            }
    }

}
