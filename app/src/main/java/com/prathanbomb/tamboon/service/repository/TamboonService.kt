package com.prathanbomb.tamboon.service.repository

import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.service.model.Request
import com.prathanbomb.tamboon.service.model.Result
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by prathanbomb on 9/14/2017 AD.
 */
interface TamboonService {
    companion object {
//        var HTTPS_API_TAMBOON_URL = "http://192.168.13.31:8080/"
        var HTTPS_API_TAMBOON_URL = "http://10.0.2.2:8080/"
    }
    @GET(".")
    fun getCharityList(): Call<List<Charity>>
    @POST("/donate")
    fun makeDonation(@Body request: Request): Call<Result>
}