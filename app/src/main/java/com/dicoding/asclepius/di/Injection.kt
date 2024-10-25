package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.Repository
import com.dicoding.asclepius.data.local.room.ClasificationDataBase
import com.dicoding.asclepius.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {
        val dataBase = ClasificationDataBase.getInstance(context)
        val dao = dataBase.clasificationDao()
        val appExecutors = AppExecutors()

        return Repository.getInstance(dao, appExecutors)
    }
}