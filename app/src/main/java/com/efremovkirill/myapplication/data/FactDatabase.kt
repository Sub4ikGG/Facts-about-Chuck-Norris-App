package com.efremovkirill.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FactModel::class], version = 1, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {

    abstract fun factDao(): FactDao

    companion object {
        @Volatile
        private var INSTANCE: FactDatabase? = null

        fun getDatabase(context: Context): FactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FactDatabase::class.java,
                    "fact_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}