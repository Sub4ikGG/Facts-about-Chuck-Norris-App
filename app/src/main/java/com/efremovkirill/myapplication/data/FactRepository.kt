package com.efremovkirill.myapplication.data

import androidx.lifecycle.LiveData

class FactRepository(private val factDao: FactDao) {

    val factData: LiveData<List<FactModel>> = factDao.getFacts()

    fun insertFact(fact: FactModel) {
        factDao.insertFact(fact)
    }

    fun getRandomFact(): FactModel? {
        return factDao.getRandomFact()
    }

    fun getFact(id: String): FactModel? {
        return factDao.getFact(id)
    }

    fun dropTable() {
        factDao.dropTable()
    }
}