package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import com.dicoding.asclepius.data.local.room.ClasificationDao
import com.dicoding.asclepius.utils.AppExecutors

class Repository private constructor(
    private val clasificationDao: ClasificationDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<ClasificationEntity>>>()

    fun getAllClasification() : LiveData<List<ClasificationEntity>> {
        result.value = Result.Loading
        return clasificationDao.getAllClasification()
    }

    fun insertClassification(clasification: ClasificationEntity) {
        appExecutors.diskIO.execute {
            clasificationDao.insertClasification(clasification)
        }
    }

    fun deleteClassification(clasificationId: Int) {
        appExecutors.diskIO.execute {
            clasificationDao.deleteClasification(clasificationId)
        }
    }

    fun deleteAllClassification() {
        appExecutors.diskIO.execute {
            clasificationDao.deleteAllClasification()
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            clasificationDao: ClasificationDao,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(clasificationDao, appExecutors)
            }.also { instance = it }
    }
}