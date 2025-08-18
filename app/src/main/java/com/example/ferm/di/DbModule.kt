package com.example.ferm.di

import android.app.Application
import androidx.room.Room
import com.example.ferm.data.AppDatabase
import com.example.ferm.data.CarritosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides @Singleton
    fun provideDb(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "mi_db").build()

    @Provides
    fun provideCarritosDao(db: AppDatabase): CarritosDao = db.carritosDao()
}