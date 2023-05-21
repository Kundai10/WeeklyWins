package com.example.wwapp.di

import android.content.Context
import androidx.room.Room
import com.example.wwapp.model.MyWinsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(context,
        MyWinsDatabase::class.java,
        "win_database"

    ).fallbackToDestructiveMigration()
        .build()
    @Provides
    @Singleton
    fun providesDao(db:MyWinsDatabase) = db.myWinsDao()


}