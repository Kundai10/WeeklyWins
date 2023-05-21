package com.example.wwapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface MyWinsDao {
    @Query("SELECT * FROM wins_table ORDER BY winDay Desc")
    fun getAllItems(): Flow<List<MyWinsItems>>
    @Query("SELECT * FROM wins_table WHERE winTitle LIKE :query")
    fun getSearchItems(query:String):Flow<List<MyWinsItems>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: MyWinsItems)
    @Update
    suspend fun updateItem(item:MyWinsItems)
    @Delete
    suspend fun deleteItem(item: MyWinsItems)
}