package com.efremovkirill.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFact(fact: FactModel)

    @Query("SELECT * FROM facts_table")
    fun getFacts(): LiveData<List<FactModel>>

    @Query("SELECT * FROM facts_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomFact(): FactModel?

    @Query("SELECT * FROM facts_table WHERE id LIKE :id")
    fun getFact(id: String): FactModel?

    @Query("DELETE FROM facts_table")
    fun dropTable()
}