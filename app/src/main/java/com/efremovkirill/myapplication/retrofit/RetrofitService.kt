package com.efremovkirill.myapplication.retrofit

import com.efremovkirill.myapplication.data.FactModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("/jokes/random")
    suspend fun getRandomFact(): Response<FactModel>
}