package com.efremovkirill.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efremovkirill.myapplication.data.FactModel
import com.efremovkirill.myapplication.retrofit.Repository

class HomeScreenViewModel : ViewModel() {
    private val retrofit = Repository()
    val factLiveData = MutableLiveData<FactModel>()

    init {
        factLiveData.postValue(
            FactModel(
                id = "ybny2hurstuzn344fgzeow",
                value = "Chuck Norris never goes to the dentist because his teeth are unbreakable. His enemies never go to the dentist because they have no teeth.",
                created_at = "2020-01-05 13:42:18",
                updated_at = "2020-01-05 13:42:18",
                url = ""
            )
        )
    }

    suspend fun getRandomFact(): FactModel? {
        val response = try {
            retrofit.getRandomFact()
        } catch (e: Exception) {
            println(e.message)
            return null
        }

        if (response.isSuccessful && response.body() != null) {
            factLiveData.postValue(response.body())
            return response.body()
        }
        return null
    }

    fun invokeFact(fact: FactModel) {
        factLiveData.postValue(fact)
    }
}