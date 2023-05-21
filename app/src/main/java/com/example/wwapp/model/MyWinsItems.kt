package com.example.wwapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wins_table")
@Parcelize
data class MyWinsItems(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val winTitle: String,
    val winDay: Long,
    val winContent: String?,
    val winImage: String?,

    ) : Parcelable

