package com.efremovkirill.myapplication.retrofit

import com.efremovkirill.myapplication.data.FactModel
import retrofit2.Response

class Repository {
    suspend fun getRandomFact(): Response<FactModel> {
        return RetrofitInstance.api.getRandomFact()
    }
}