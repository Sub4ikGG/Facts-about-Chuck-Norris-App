package com.efremovkirill.myapplication.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "facts_table")
data class FactModel(
    @PrimaryKey(autoGenerate = true)
    val i_d: Int = 0,

    val id: String,
    val value: String,
    val created_at: String,
    val updated_at: String,
    val url: String
): Parcelable
