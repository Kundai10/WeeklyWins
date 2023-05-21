package com.example.wwapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities=[MyWinsItems::class], version=1, exportSchema = false)
abstract class MyWinsDatabase:RoomDatabase() {
    abstract fun myWinsDao():MyWinsDao
}