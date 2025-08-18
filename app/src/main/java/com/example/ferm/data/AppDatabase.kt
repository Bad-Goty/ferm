package com.example.ferm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ferm.data.entity.CarritoEntity

@Database(entities = [CarritoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carritosDao(): CarritosDao
}