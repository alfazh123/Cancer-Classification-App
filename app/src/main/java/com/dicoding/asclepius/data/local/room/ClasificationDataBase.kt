package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.ClasificationEntity

@Database(entities = [ClasificationEntity::class], version = 1, exportSchema = false)
abstract class ClasificationDataBase: RoomDatabase() {
    abstract fun clasificationDao(): ClasificationDao

    companion object {
        @Volatile
        private var instance: ClasificationDataBase? = null
        fun getInstance(context: Context): ClasificationDataBase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ClasificationDataBase::class.java, "Clasification.db"
                ).build()
            }
    }
}